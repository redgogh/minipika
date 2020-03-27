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
import org.raniaia.approve.framework.exception.ApproveException;
import org.raniaia.available.list.Lists;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

/*
 * 支持分布式的数据源, 可以对多个库进行查询。
 */

/**
 * Distributed datasource.
 *
 * @author tiansheng
 */
public class DistributeDatasource implements DataSource {

    /*
     * 正在被使用的数据源
     */
    /**
     * Data source being used.
     */
    DataSource activeDataSource;

    List<DataSource> dataSources = Lists.newArrayList();

    public DistributeDatasource(IDataSource... iDataSources) {
        List<DataSource> creates = Lists.newArrayList();
        for (IDataSource iDataSource : iDataSources) {
            if ("POOLED".equals(iDataSource.getSourceType())) {
                creates.add(new PooledDataSource(iDataSource));
                continue;
            }
            if ("UNPOOLED".equals(iDataSource.getSourceType())) {
                creates.add(new UnpooledDatasource(iDataSource));
            }
        }
    }

    public DistributeDatasource(Collection<? extends DataSource> pooledDataSources) {
        dataSources.addAll(pooledDataSources);
    }

    /*
     * 手动添加数据源
     */
    public void addDataSource(DataSource dataSource) {
        dataSources.add(dataSource);
    }

    /*
     * 切换当前正在使用的数据源
     */
    public void switchDataSource(DataSource dataSource) {
        this.activeDataSource = dataSource;
        if (!dataSources.contains(dataSource)) {
            dataSources.add(dataSource);
        }
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
