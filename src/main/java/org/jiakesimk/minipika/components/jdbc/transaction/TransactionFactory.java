package org.jiakesimk.minipika.components.jdbc.transaction;

/* ************************************************************************
 *
 * Copyright (C) 2020 2B键盘 All rights reserved.
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

/**
 * 如果使用了事务管理器，那么框架将假定你的{@link Connection#getAutoCommit()}值
 * 是为true的，如果AutoCommit属性不为true则没办法启动事务管理。
 *
 * @author 2B键盘
 * @email jiakesiws@gmail.com
 */
public interface TransactionFactory {

  /**
   * 设置事务的隔离级别
   */
  void setTransactionIsolationLevel(TransactionIsolationLevel level);

  /**
   * 凡是通过事务管理器创建的连接都将被挂在上事务
   *
   * @param dataSource 数据源，连接需要数据源去创建
   * @return 事务管理器
   */
  Transaction newTransaction(DataSource dataSource);

}
