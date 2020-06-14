package org.jiakesimk.minipika.components.jdbc.datasource.unpooled;

/*
 * Copyright (C) 2020 tiansheng All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * Creates on 2020/6/1.
 */


import lombok.Getter;
import lombok.Setter;
import org.jiakesimk.minipika.components.jdbc.datasource.DataSourceManager;
import org.jiakesimk.minipika.framework.PropertyNames;
import org.jiakesimk.minipika.framework.configuration.node.SingleDataSource;
import org.jiakesimk.minipika.framework.loader.CopyingClassLoader;
import org.jiakesimk.minipika.framework.logging.Log;
import org.jiakesimk.minipika.framework.logging.LogFactory;
import org.jiakesimk.minipika.framework.util.ClassUtils;
import org.jiakesimk.minipika.framework.util.StringUtils;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Properties;
import java.util.concurrent.*;
import java.util.logging.Logger;

/**
 * 未池化的数据源
 *
 * @author tiansheng
 */
public class UnpooledDataSource implements DataSource {

  protected Driver driver;

  @Setter
  @Getter
  protected SingleDataSource dataSource;

  protected Properties properties = new Properties();

  public UnpooledDataSource() {
    this(null);
  }

  public UnpooledDataSource(SingleDataSource dataSource) {
    this.dataSource = dataSource;
    DataSourceManager.registerDataSource(getDataSource().getName(), this);
  }

  private Connection doGetConnection(String username, String password) throws SQLException {
    initializeDriver();
    properties.setProperty(SingleDataSource.USERNAME, username);
    properties.setProperty(SingleDataSource.PASSWORD, password);
    Connection connection = driver.connect(dataSource.getUrl(), properties);
    return configurationConnection(connection);
  }

  private Connection configurationConnection(Connection connection) throws SQLException {
    if (dataSource.isDesiredAutoCommit() != connection.getAutoCommit()) {
      connection.setAutoCommit(dataSource.isDesiredAutoCommit());
    }
    return connection;
  }

  /**
   * 初始化驱动程序
   */
  private synchronized void initializeDriver() throws SQLException {
    if (this.driver == null) {
      try {
        Class<?> driverClass = Class.forName(dataSource.getDriver(), true, new CopyingClassLoader());
        Driver driver0 = (Driver) ClassUtils.newInstance(driverClass);
        Driver driverProxy = new DriverProxy(driver0);
        DriverManager.registerDriver(driverProxy);
        this.driver = driverProxy;
      } catch (Exception e) {
        throw new SQLException("Error failed to initialize driver. Cause: " + e.getMessage());
      }
    }
  }

  /**
   * 驱动代理
   */
  static class DriverProxy implements Driver {

    Driver d;

    final Log LOG = LogFactory.getLog(DriverProxy.class);

    DriverProxy(Driver d) {
      this.d = d;
    }

    @Override
    public Connection connect(String url, Properties info) throws SQLException {
      Connection connection = null;
      if (LOG.isDebugEnabled()) {
        LOG.debug("using url: " + url);
      }
      String name = info.getProperty(SingleDataSource.USERNAME);
      String pass = info.getProperty(SingleDataSource.PASSWORD);
      Executor executor = Executors.newSingleThreadExecutor();
      FutureTask<Connection> futureTask = new FutureTask<>(new Callable<Connection>() {
        @Override
        public Connection call() throws Exception {
          return DriverManager.getConnection(url, name, pass);
        }
      });
      executor.execute(futureTask);
      String connectTimeout = System.getProperty(PropertyNames.CONNECT_TIMEOUT);
      // 设置超时时间
      long timeout = StringUtils.asLong(connectTimeout);
      if (timeout == 0) {
        timeout = 15L;
      }
      try {
        connection = futureTask.get(timeout, TimeUnit.SECONDS);
      } catch (InterruptedException | TimeoutException | ExecutionException e) {
        if (e instanceof TimeoutException) {
          LOG.error("Error get connection timeout. Cause: set timeout is " + timeout + " seconds.");
        }
        e.printStackTrace();
      }
      return connection;
    }

    @Override
    public boolean acceptsURL(String url) throws SQLException {
      return d.acceptsURL(url);
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
      return d.getPropertyInfo(url, info);
    }

    @Override
    public int getMajorVersion() {
      return d.getMajorVersion();
    }

    @Override
    public int getMinorVersion() {
      return d.getMinorVersion();
    }

    @Override
    public boolean jdbcCompliant() {
      return d.jdbcCompliant();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
      return d.getParentLogger();
    }
  }

  @Override
  public Connection getConnection() throws SQLException {
    return doGetConnection(dataSource.getUsername(), dataSource.getPassword());
  }

  @Override
  public Connection getConnection(String username, String password) throws SQLException {
    return doGetConnection(username, password);
  }

  @Override
  public PrintWriter getLogWriter() throws SQLException {
    return DriverManager.getLogWriter();
  }

  @Override
  public void setLogWriter(PrintWriter out) throws SQLException {
    DriverManager.setLogWriter(out);
  }

  @Override
  public void setLoginTimeout(int seconds) throws SQLException {
    DriverManager.setLoginTimeout(seconds);
  }

  @Override
  public int getLoginTimeout() throws SQLException {
    return DriverManager.getLoginTimeout();
  }

  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {
    throw new SQLException(getClass().getName() + " is not a wrapper");
  }

  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    return false;
  }

  @Override
  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
    return Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
  }

}
