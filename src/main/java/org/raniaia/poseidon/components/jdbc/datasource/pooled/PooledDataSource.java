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

import com.sun.xml.internal.bind.v2.model.core.ID;
import org.raniaia.poseidon.components.jdbc.datasource.unpooled.IDataSource;
import org.raniaia.poseidon.components.jdbc.datasource.unpooled.UnpooledDatasource;

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

    private final PoolState state = new PoolState(this);

    // Unpooled DataSource.
    private final UnpooledDatasource datasource;

    /**
     * Maximum idle connections.
     */
    int poolMaximumIdleConnections = 0;

    /**
     * Minimum idle connections.
     */
    int poolMinimumIdleConnections = 0;

    public PooledDataSource(){
        this.datasource = new UnpooledDatasource();
    }

    public PooledDataSource(IDataSource iDataSource){
        this.datasource = new UnpooledDatasource(iDataSource);
    }

    /**
     * Pop connection.
     */
    // 弹出链接
    private PooledConnection popConnection(String username, String password) throws SQLException {
        return null;
    }

    /**
     * Return connection.
     */
    // 归还链接
    protected void pushConnection(PooledConnection conn) throws SQLException {
        synchronized (state) {
            state.activeConnections.remove(conn);
            if(conn.isValid()){
                if (state.idleConnection.size() < poolMaximumIdleConnections)
                state.idleConnection.add(conn);
                PooledConnection newConn = new PooledConnection(conn.getRealConnection(),this);
                state.idleConnection.add(newConn);
                state.notifyAll();
            }else{

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
