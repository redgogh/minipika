package org.raniaia.poseidon.components.jdbc.datasource.pooled;

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
import org.raniaia.poseidon.components.jdbc.datasource.unpooled.IDataSource;
import org.raniaia.poseidon.components.jdbc.datasource.unpooled.UnpooledDatasource;
import org.raniaia.poseidon.components.log.Log;
import org.raniaia.poseidon.components.log.LogFactory;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * 仅支持连接池的数据源
 * Only support connection pool the datasource.
 *
 * @author tiansheng
 */
public class PooledDataSource implements DataSource {


    @Getter
    private final PoolState state = new PoolState(this);

    private final static Log log = LogFactory.getLog(PooledConnection.class);

    // Unpooled DataSource.
    private final UnpooledDatasource datasource;

    /**
     * Maximum idle connections.
     */
    int poolMaximumIdleConnections = 10;

    /**
     * Minimum idle connections.
     */
    int poolMinimumIdleConnections = 2;

    long poolTimeToWait = 20000L;

    public PooledDataSource() {
        this.datasource = new UnpooledDatasource();
    }

    public PooledDataSource(IDataSource iDataSource) {
        this.datasource = new UnpooledDatasource(iDataSource);
        try {
            for (int i = 0; i < poolMinimumIdleConnections; i++) {
                state.idleConnections.add(new PooledConnection(datasource.getConnection(), this));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PooledConnection popConnection() throws SQLException {
        return popConnection(datasource.getIDataSource().getUsername(),
                datasource.getIDataSource().getPassword());
    }

    /**
     * Pop connection.
     */
    // 弹出链接
    private PooledConnection popConnection(String username, String password) throws SQLException {
        PooledConnection conn = null;
        long time = System.currentTimeMillis();
        //
        // If this variable value > 3, then give up get connection.
        // And throw exception.
        //
        long localBadConnectionCount = 0L;
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
                        localBadConnectionCount++;
                        conn.forceClose(); // 强制关闭链接 | force close.
                        conn = null;
                        if (localBadConnectionCount > (6)) {
                            if (log.isDebugEnabled()) {
                                log.debug("PooledDataSource: Could not get a good connection to the database.");
                            }
                            throw new SQLException("PooledDataSource: Could not get a good connection to the database.");
                        }
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
                    conn.invalidate();
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
        return null;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
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
}
