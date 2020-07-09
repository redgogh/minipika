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
import org.jiakesimk.minipika.components.configuration.node.DataSourceConfiguration;
import org.jiakesimk.minipika.components.logging.Log;
import org.jiakesimk.minipika.components.logging.LogFactory;
import org.jiakesimk.minipika.framework.util.ClassUtils;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * 未池化的数据源
 *
 * @author tiansheng
 */
public class UnpooledDataSource implements DataSource {

  protected Driver driver;

  protected DataSourceConfiguration configuration;

  protected Properties properties = new Properties();

  public UnpooledDataSource() {
    this(null);
  }

  public UnpooledDataSource(DataSourceConfiguration configuration) {
    this.configuration = configuration;
    DataSourceManager.registerDataSource(getConfiguration().getName(), this);
  }

  public DataSourceConfiguration getConfiguration() {
    return configuration;
  }

  public void setConfiguration(DataSourceConfiguration configuration) {
    this.configuration = configuration;
  }

  private Connection doGetConnection(String username, String password) throws SQLException {
    initializeDriver();
    properties.setProperty(DataSourceConfiguration.USERNAME, username);
    properties.setProperty(DataSourceConfiguration.PASSWORD, password);
    Connection connection = driver.connect(configuration.getUrl(), properties);
    return configurationConnection(connection);
  }

  private Connection configurationConnection(Connection connection) throws SQLException {
    if (configuration.isDesiredAutoCommit() != connection.getAutoCommit()) {
      connection.setAutoCommit(configuration.isDesiredAutoCommit());
    }
    return connection;
  }

  /**
   * 初始化驱动程序
   */
  private synchronized void initializeDriver() throws SQLException {
    if (this.driver == null) {
      try {
        Class<?> driverClass = Class.forName(configuration.getDriver(), true, this.getClass().getClassLoader());
        Driver driver0 = (Driver) ClassUtils.newInstance(driverClass);
        DriverManager.registerDriver(driver0);
        this.driver = new DriverProxy(driver0);
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
      if (LOG.isDebugEnabled()) {
        LOG.debug(" using url: " + url);
      }
      String name = info.getProperty(DataSourceConfiguration.USERNAME);
      String pass = info.getProperty(DataSourceConfiguration.PASSWORD);
      return DriverManager.getConnection(url, name, pass);
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
    return doGetConnection(configuration.getUsername(), configuration.getPassword());
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
