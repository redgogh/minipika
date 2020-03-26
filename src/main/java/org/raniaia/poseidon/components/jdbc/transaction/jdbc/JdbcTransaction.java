package org.raniaia.poseidon.components.jdbc.transaction.jdbc;

/*
 * Copyright (C) 2020 Tiansheng All rights reserved.
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
 * Creates on 2020/1/30.
 */

import org.raniaia.poseidon.components.jdbc.transaction.Transaction;
import org.raniaia.poseidon.components.jdbc.transaction.TransactionIsolationLevel;
import org.raniaia.poseidon.components.log.Log;
import org.raniaia.poseidon.components.log.LogFactory;
import org.raniaia.poseidon.framework.provide.component.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * This class refers to the Mybatis source code.
 *
 * @author tiansheng
 */
@Component
public class JdbcTransaction implements Transaction {

    private DataSource dataSource;
    private Connection connection;
    private boolean desiredAutoCommit;
    private TransactionIsolationLevel level;

    private static final Log LOG = LogFactory.getLog(JdbcTransaction.class);

    public JdbcTransaction(Connection connection) {
        this.connection = connection;
    }

    public JdbcTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean desiredAutoCommit) {
        this.dataSource = dataSource;
        this.level = level;
        this.desiredAutoCommit = desiredAutoCommit;
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (connection == null) {
            openConnection();
        }
        return connection;
    }

    @Override
    public void commit() throws SQLException {
        if (connection != null && !connection.getAutoCommit()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(connection + " connection execute commit");
            }
            connection.commit();
        }
    }

    @Override
    public void rollback() throws SQLException {
        if (connection != null && !connection.getAutoCommit()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Rolling back JDBC Connection [" + connection + "]");
            }
            connection.rollback();
        }
    }

    @Override
    public void close() throws SQLException {
        if (connection != null) {
            resetAutoCommit();
            if (LOG.isDebugEnabled()) {
                LOG.debug("Closing JDBC Connection [" + connection + "]");
            }
            connection.close();
        }
    }

    protected void resetAutoCommit() {
        try {
            if (!connection.getAutoCommit()) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Resetting autocommit to true on JDBC Connection [" + connection + "]");
                }
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            LOG.debug("Error resetting autocommit to true "
                    + "before closing the connection.  Cause: " + e);
        }
    }

    protected void setDesiredAutoCommit(boolean desiredAutoCommit) throws SQLException {
        if (connection.getAutoCommit() != desiredAutoCommit) {
            connection.setAutoCommit(desiredAutoCommit);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Setting autocommit to " + desiredAutoCommit + " on JDBC Connection [" + connection + "]");
            }
        }
    }

    private void openConnection() throws SQLException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Opening jdbc connection.");
        }
        Connection conn = dataSource.getConnection();
        if (level != null) {
            conn.setTransactionIsolation(level.getLevel());
        }
        setDesiredAutoCommit(desiredAutoCommit);
    }

}
