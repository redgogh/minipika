package org.raniaia.poseidon.components.jdbc.datasource.unpooled;

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
 * Creates on 2020/3/25.
 */

import org.raniaia.available.map.Maps;
import org.raniaia.poseidon.framework.loader.NativeClassLoader;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * 不支持连接池的数据源
 * Not support connection pool the datasource.
 *
 * @author tiansheng
 */
public class UnpooledDatasource implements DataSource {

    // 驱动属性
    private Properties driverProperties;
    private NativeClassLoader nativeClassLoader;

    // 存放驱动实例
    private static Map<String, Driver> registerDrivers = Maps.newConcurrentHashMap();

    // 保存URL，DRIVER等属性的POJO
    private IDataSource iDataSource;

    public UnpooledDatasource() {
    }

    public UnpooledDatasource(IDataSource iDataSource) {
        this.iDataSource = iDataSource;
    }

    private Connection doGetConnection() {
        return doGetConnection();
    }

    /**
     * 初始化当前{@link IDataSource#driver}中的驱动
     */
    private synchronized void initializeDriver() throws SQLException {
        if (!registerDrivers.containsKey(iDataSource.driver)) {
            Class<?> driver = null;
            try {
                Class.forName(iDataSource.driver, true, nativeClassLoader);
                Driver driverInstance = (Driver) driver.newInstance();
                DriverManager.registerDriver(new DriverProxy(driverInstance));
                registerDrivers.put(iDataSource.driver, driverInstance);
            } catch (Exception e) {
                throw new SQLException("Error setting jdbc driver on UnpooledDataSource. Cause: " + e);
            }
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return null;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
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
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    /**
     * 驱动代理类
     */
    class DriverProxy implements Driver {

        final Driver d;

        DriverProxy(Driver d) {
            this.d = d;
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
