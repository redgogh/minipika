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

import org.jiakesimk.minipika.framework.factory.Factorys;

import javax.sql.DataSource;

/**
 * 默认的事务创建工厂类
 *
 * @author 2B键盘
 * @email jiakesiws@gmail.com
 */
public class JdbcTransactionFactory implements TransactionFactory {

  private TransactionIsolationLevel level;

  @Override
  public void setTransactionIsolationLevel(TransactionIsolationLevel level) {
    this.level = level;
  }

  @Override
  public Transaction newTransaction(DataSource dataSource) {
    Transaction transaction = Factorys.forClass(Transaction.class);
    transaction.setDataSource(dataSource);
    transaction.setTransactionIsolationLevel(level);
    return transaction;
  }

}
