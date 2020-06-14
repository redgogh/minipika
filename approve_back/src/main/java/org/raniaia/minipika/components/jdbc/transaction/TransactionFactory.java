package org.jiakesimk.minipika.components.jdbc.transaction;

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
 * Creates on 2020/3/26.
 */

import org.jiakesimk.minipika.components.jdbc.NativeJdbcImpl;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Properties;

/*
 * 事务工厂
 */
/**
 * Transaction factory.
 *
 * @author tiansheng
 */
public interface TransactionFactory {

    /*
     * 设置属性
     */
    /**
     * Set transaction properties.
     */
    void setProperties(Properties properties);

    /*
     * 设置事务的隔离级别
     */
    /**
     * Set transaction isolation level.
     */
    void setTransactionIsolationLevel(TransactionIsolationLevel level);

    /*
     * 获取事务的隔离级别
     */
    /**
     * Get transaction isolation level.
     */
    TransactionIsolationLevel getTransactionIsolationLevel();

    /*
     * 创建一个新的事务管理器, 通过{@link Connection}创建
     */
    /**
     * Create a new transaction manager.
     *
     * @param connection by connection.
     */
    Transaction newTransaction(Connection connection);

    /*
     * 创建一个新的事务管理器, 通过数据源和是否自动提交来设置
     */
    /**
     * Create a new transaction manager.
     *
     * @param dataSource            The dataSource.
     * @param desiredAutoCommit     The AutoCommit.
     *
     * @see NativeJdbcImpl#execute}
     * @see NativeJdbcImpl#executeQuery}
     * @see NativeJdbcImpl#executeUpdate}
     * @see NativeJdbcImpl#executeBatch}
     *
     * @return new transaction manager.
     */
    Transaction newTransaction(DataSource dataSource, boolean desiredAutoCommit);

    /*
     * 创建一个新的事务管理器, 通过数据源和是否自动提交来设置
     */
    /**
     * Create a new transaction manager.
     *
     * @param dataSource            The dataSource.
     * @param level                 The jdbc isolation level.
     * @param desiredAutoCommit     The AutoCommit.
     *
     * @see NativeJdbcImpl#execute}
     * @see NativeJdbcImpl#executeQuery}
     * @see NativeJdbcImpl#executeUpdate}
     * @see NativeJdbcImpl#executeBatch}
     *
     * @return new transaction manager.
     */
    Transaction newTransaction(DataSource dataSource,TransactionIsolationLevel level, boolean desiredAutoCommit);

}
