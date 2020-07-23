package org.jiakesimk.minipika.components.jdbc.transaction.jdbc;

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
 * Creates on 2020/3/26.
 */

import org.jiakesimk.minipika.components.jdbc.transaction.Transaction;
import org.jiakesimk.minipika.components.jdbc.transaction.TransactionFactory;
import org.jiakesimk.minipika.components.jdbc.transaction.TransactionIsolationLevel;
import org.jiakesimk.minipika.framework.provide.component.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Properties;

/**
 * @author tiansheng
 * @email jiakesiws@gmail.com
 */
@Component
public class JdbcTransactionFactory implements TransactionFactory {

    private TransactionIsolationLevel level;

    @Override
    public void setProperties(Properties properties) {

    }

    @Override
    public void setTransactionIsolationLevel(TransactionIsolationLevel level) {
        this.level = level;
    }

    @Override
    public TransactionIsolationLevel getTransactionIsolationLevel() {
        return this.level;
    }

    @Override
    public Transaction newTransaction(Connection connection) {
        return new JdbcTransaction(connection);
    }

    @Override
    public Transaction newTransaction(DataSource dataSource, boolean desiredAutoCommit) {
        return new JdbcTransaction(dataSource, level, desiredAutoCommit);
    }

    @Override
    public Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean desiredAutoCommit) {
        return new JdbcTransaction(dataSource, level, desiredAutoCommit);
    }

}
