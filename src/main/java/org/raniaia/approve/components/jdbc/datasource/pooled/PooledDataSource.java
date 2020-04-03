package org.raniaia.approve.components.jdbc.datasource.pooled;

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

import lombok.Getter;
import org.raniaia.approve.components.jdbc.datasource.unpooled.Dsi;
import org.raniaia.approve.components.jdbc.datasource.unpooled.UnpooledDatasource;
import org.raniaia.approve.components.logging.Log;
import org.raniaia.approve.components.logging.LogFactory;
import org.raniaia.approve.framework.provide.component.Component;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * 仅支持连接池的数据源
 * Only support connection pool the datasource.
 *
 * @author tiansheng
 */
@Component
public class PooledDataSource implements DataSource {

    @Getter
    private final PoolState         state       =       new PoolState(this);

    private final static Log        log         =       LogFactory.getLog(PooledConnection.class);

    // Unpooled DataSource.
    private final UnpooledDatasource datasource;

    /*
     * 最大空闲连接
     */
    /**
     * Maximum idle connections.
     */
    int poolMaximumIdleConnections = 50;

    /*
     * 最小空闲连接
     */
    /**
     * Minimum idle connections.
     */
    int poolMinimumIdleConnections = 2;

    /*
     * 如果一个连接存在的时间过长，就将它销毁掉，重新创建。
     * 默认时间为3小时
     */
    /**
     * Connection maximum survive time. 3H
     */
    long maximumTimestamp = 10800L;

    long poolTimeToWait = 2000L;

    public PooledDataSource() {
        this.datasource = new UnpooledDatasource();
    }

    public PooledDataSource(Dsi dsi) {
        this.datasource = new UnpooledDatasource(dsi);
        try {
            for (int i = 0; i < poolMinimumIdleConnections; i++) {
                state.idleConnections.add(new PooledConnection(datasource.getConnection(), this));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PooledConnection popConnection() throws SQLException {
        return popConnection(datasource.getDsi().getUsername(),
                datasource.getDsi().getPassword());
    }

    /**
     * Pop connection.
     */
    // 弹出链接
    private PooledConnection popConnection(String username, String password) throws SQLException {
        PooledConnection conn = null;
        long time = System.currentTimeMillis();
        // endless loop, until a connection is found.
        while (conn == null) {
            synchronized (state) {
                if (!state.idleConnections.isEmpty()) {
                    conn = state.idleConnections.remove(0);
                    if (log.isDebugEnabled()) {
                        log.debug("Pop out connection " + conn.getRealHasCode() + " from pool.");
                    }
                } else {
                    // if idle connections equals null.
                    if (state.activeConnections.size() < poolMaximumIdleConnections) {
                        conn = new PooledConnection(datasource.getConnection(username, password), this);
                        state.accumulateCreateCount++;
                        if (log.isDebugEnabled()) {
                            log.debug("Creates connection " + conn.getRealHasCode() + ".");
                        }
                    } else {
                        //
                        // if the connection is not enough.
                        // then current thread join wait status.
                        //
                        // 如果链接已经不够使用了，那么当前线程就进入等待状态。
                        //
                        try {
                            state.hadToWaitCount++;
                            if (log.isDebugEnabled()) {
                                log.debug("Waiting as long as " + poolTimeToWait + "milliseconds for connection.");
                            }
                            long wt = System.currentTimeMillis();
                            state.wait(poolTimeToWait);
                            state.accumulateWaitTime += (System.currentTimeMillis() - wt);
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                }
                // if connection not equals null.
                if (conn != null) {
                    if (conn.isValid()) {
                        conn.setLastUsedTimestamp(System.currentTimeMillis());
                        state.activeConnections.add(conn);
                        state.requestCount++;
                        state.requestAccumulateTime += (System.currentTimeMillis() - time);
                    } else {
                        if (log.isDebugEnabled()) {
                            log.debug("A bad connection (" + conn.getRealHasCode() + ") close. return to another connection.");
                        }
                        state.badConnectionCount++;
                        conn.forceClose(); // 强制关闭链接 | force close.
                        conn = null;
                    }
                }
            }
        }
        if (conn == null) {
            if (log.isDebugEnabled()) {
                log.debug("Unknown severe error condition. The connection pool returned a null connection.");
            }
            throw new SQLException("Unknown severe error condition. The connection pool returned a null connection.");
        }
        return conn;
    }

    /**
     * Return connection.
     */
    // 归还链接
    protected void pushConnection(PooledConnection conn) throws SQLException {
        synchronized (state) {
            state.activeConnections.remove(conn);
            if (conn.isValid()) {
                if (state.idleConnections.size() < poolMaximumIdleConnections) {
                    state.idleConnections.add(conn);
                    PooledConnection newConn = new PooledConnection(conn.getRealConnection(), this);
                    newConn.setCreateTimestamp(System.currentTimeMillis());
                    state.idleConnections.add(newConn);
                    if(conn.checkoutTimestamp() > maximumTimestamp){
                        conn.invalidate();
                    }
                    if (log.isDebugEnabled()) {
                        log.debug("Returned connection " + newConn.getRealHasCode() + " to pool.");
                    }
                    state.notifyAll();
                } else {
                    conn.invalidate();
                    if (log.isDebugEnabled()) {
                        log.debug("Closed a connection " + conn.getRealHasCode() + ".");
                    }
                }
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("A bad connection (" + conn.getRealHasCode() + ") failure to join connection pool.");
                }
                state.badConnectionCount++;
            }
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        //覆盖了DataSource.getConnection方法，每次都是pop一个Connection，即从池中取出一个来
        return popConnection().getProxyConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return popConnection(username, password).getProxyConnection();
    }

    @Override
    public void setLoginTimeout(int loginTimeout) throws SQLException {
        DriverManager.setLoginTimeout(loginTimeout);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return DriverManager.getLoginTimeout();
    }

    @Override
    public void setLogWriter(PrintWriter logWriter) throws SQLException {
        DriverManager.setLogWriter(logWriter);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return DriverManager.getLogWriter();
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
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    }
}
