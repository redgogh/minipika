package org.jiakesimk.minipika.components.jdbc.transaction;

/* ************************************************************************
 *
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
 *
 * ************************************************************************/

/*
 * Creates on 2020/6/6.
 */

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 事务管理器接口
 *
 * @author tiansheng
 * @email jiakesiws@gmail.com
 */
public interface Transaction {

  /**
   * 设置数据源
   *
   * @param dataSource 数据源
   */
  void setDataSource(DataSource dataSource);

  /**
   * @return 获取数据源
   */
  DataSource getDataSource();

  /**
   * 获取连接
   *
   * @return JDBC连接
   */
  Connection getConnection();

  /**
   * 获取一个连接，但是这个连接不需要挂载事务
   *
   * @return #Connection
   * @
   */
  Connection getConnection(boolean openTransaction);

  /**
   * 设置事务隔离级别
   *
   * @param level {@link TransactionIsolationLevel}
   * @see TransactionIsolationLevel#TRANSACTION_NONE
   * @see TransactionIsolationLevel#TRANSACTION_READ_COMMITTED
   * @see TransactionIsolationLevel#TRANSACTION_READ_UNCOMMITTED
   * @see TransactionIsolationLevel#TRANSACTION_REPEATABLE_READ
   * @see TransactionIsolationLevel#TRANSACTION_SERIALIZABLE
   */
  void setTransactionIsolationLevel(TransactionIsolationLevel level);

  /**
   * 获取当前事务管理器的事务隔离级别
   *
   * @return 事务隔离级别
   */
  TransactionIsolationLevel getTransactionIsolationLevel();

  /**
   * 提交信息
   */
  void commit() throws SQLException;

  /**
   * 事务回滚
   */
  void rollback() throws SQLException;

  /**
   * 连接关闭
   */
  void close() throws SQLException;

}
