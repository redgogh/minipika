package org.raniaia.approve.components.jdbc.datasource.distribute;

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

import org.raniaia.approve.components.jdbc.datasource.pooled.PooledDataSource;
import org.raniaia.approve.components.jdbc.datasource.unpooled.IDataSource;
import org.raniaia.approve.components.jdbc.datasource.unpooled.UnpooledDatasource;
import org.raniaia.approve.components.logging.Log;
import org.raniaia.approve.components.logging.LogFactory;
import org.raniaia.approve.framework.exception.ApproveException;
import org.raniaia.approve.framework.tools.Maps;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.*;
import java.util.logging.Logger;

/*
 * 支持分布式的数据源, 可以对多个库进行查询。
 * 可以在项目运行时进行对库的切换，也可以通过事务管理器来进行自动的切换。
 * 在负载均衡策略上也能够做到灵活的对多个库进行切换查询。
 */

/**
 * Distributed datasource.
 *
 * @author tiansheng
 */
public class DistributeDataSource implements DataSource {

    /*
     * 正在被使用的数据源
     */
    /**
     * Data source being used.
     */
    DataSource activeDataSource;

    Map<Object, DataSource> dataSources = Maps.newHashMap();

    static final Log log = LogFactory.getLog(DistributeDataSource.class);

    public DistributeDataSource(IDataSource... iDataSources) {
        for (IDataSource iDataSource : iDataSources) {
            addDataSource(iDataSource);
        }
    }

    /*
     * 手动添加数据源
     */
    public void addDataSource(IDataSource dataSource) {
        dataSources.put(dataSource.getId(), createDataSource(dataSource));
    }

    // 根据IDataSource中的sourceType字段去创建一个DataSource实例
    public DataSource createDataSource(IDataSource iDataSource) {
        if ("POOLED".equals(iDataSource.getSourceType())) {
            return new PooledDataSource(iDataSource);
        }
        if ("UNPOOLED".equals(iDataSource.getSourceType())) {
            return new UnpooledDatasource(iDataSource);
        }
        log.error("Error DistributeDataSource create PooledDataSource or UnpooledDataSource failure. " +
                "Cause: IDataSource#sourceType mistaken, please check your sourceType parameter.");

        throw new ApproveException("Error DistributeDataSource create PooledDataSource or UnpooledDataSource failure. " +
                "Cause: IDataSource#sourceType mistaken, please check your sourceType parameter.");
    }

    /*
     * 切换当前正在使用的数据源
     */
    public void switchDataSource(Object id) {
        DataSource dataSource = dataSources.get(id);
        this.activeDataSource = Objects.requireNonNull(dataSource,"wrong datasource id.");
    }

    @Override
    public Connection getConnection() throws SQLException {
        return activeDataSource.getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return activeDataSource.getConnection(username, password);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new ApproveException("`Error DistributeDataSource not support #unwrap method.");
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new ApproveException("`Error DistributeDataSource not support #isWrapperFor method.");
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
        throw new ApproveException("`Error DistributeDataSource not support #getParentLogger method.");
    }
}
