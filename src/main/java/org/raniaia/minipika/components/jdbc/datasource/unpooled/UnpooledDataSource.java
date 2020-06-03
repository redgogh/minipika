package org.raniaia.minipika.components.jdbc.datasource.unpooled;

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

import org.raniaia.minipika.components.jdbc.datasource.DatabaseSupport;
import org.raniaia.minipika.framework.factory.Factorys;
import org.raniaia.minipika.framework.util.StringUtils;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author tiansheng
 */
public class UnpooledDataSource implements DataSource {

  protected SourceInfo source;

  public UnpooledDataSource() {
  }

  public UnpooledDataSource(SourceInfo source) {
    this.source = source;
  }

  @Override
  public Connection getConnection() throws SQLException {
    return doGetConnection(source.username, source.password);
  }

  @Override
  public Connection getConnection(String username, String password) throws SQLException {
    return doGetConnection(username, password);
  }

  private Connection doGetConnection(String username, String password) throws SQLException {
    initializeDriver();
    Driver driver = SourceInfo.registerDrivers.get(source.driver);
    Connection connection = driver.connect(source.url, source.buildDriverInfo(username, password));
    configurationConnection(connection);
    return connection;
  }

  private void configurationConnection(Connection connection) throws SQLException {
    // 是否自动提交
    if (source.autoCommit != null && source.autoCommit != connection.getAutoCommit()) {
      connection.setAutoCommit(source.autoCommit);
    }
  }

  private synchronized void initializeDriver() throws SQLException {
    if (!SourceInfo.registerDrivers.containsKey(source.driver)) {
      Class<?> driver = null;
      try {
        driver = Class.forName(source.driver, true, source.driverClassLoader);
        Driver driverInstance = (Driver) Factorys.forClass(driver);
        //
        // 代理的作用是防止在多线程环境下实例化驱动导致死锁问题
        //
        DriverManager.registerDriver(new DriverProxy(driverInstance));
        SourceInfo.registerDrivers.put(source.driver, driverInstance);
      } catch (Exception e) {
        throw new SQLException("在未池化得数据源中设置JDBC驱动异常, 原因: " + e.getMessage());
      }
    }
  }

  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {
    throw new SQLException(getClass().getName() + " is not a wrapper.");
  }

  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    return false;
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
  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
    return Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
  }

  /**
   * 驱动代理
   */
  class DriverProxy implements Driver {

    Driver d;

    DriverProxy(Driver driver) {
      this.d = driver;
    }

    @Override
    public Connection connect(String url, Properties info) throws SQLException {
      return d.connect(url, info);
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

}
