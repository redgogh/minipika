package org.raniaia.minipika.components.jdbc.transaction;

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
 * Creates on 2020/6/6.
 */

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * 如果使用了事务管理器，那么系统将假定你的{@link Connection#getAutoCommit()}值
 * 是为true的，如果AutoCommit属性不为true则没办法启动事务管理。
 *
 * @author tiansheng
 */
public interface TransactionFactory {

  /**
   * 设置事务的隔离级别
   */
  void setTransactionIsolationLevel(TransactionIsolationLevel level);

  /**
   * 给特定的单个连接挂在事务
   *
   * @param connection 需要事务支持的连接
   * @return 事务管理器
   */
  Transaction newTransaction(Connection connection);

  /**
   * 给多个特定的连接挂在上事务
   *
   * @param connections 连接集合
   * @return 事务管理器
   */
  Transaction newTransaction(Connection... connections);

  /**
   * 凡是通过事务管理器创建的连接都将被挂在上事务
   *
   * @param dataSource 数据源，连接需要数据源去创建
   * @return 事务管理器
   */
  Transaction newTransaction(DataSource dataSource);

  /**
   * 指定事务的隔离级别
   *
   * @param dataSource 数据源，用于创建连接等作用
   * @param level      事务隔离级别
   * @return 事务管理器
   */
  Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level);

}
