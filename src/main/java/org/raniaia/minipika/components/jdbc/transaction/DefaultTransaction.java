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

import org.raniaia.minipika.components.jdbc.datasource.pooled.PooledConnection;
import org.raniaia.minipika.framework.logging.Log;
import org.raniaia.minipika.framework.logging.LogFactory;

import javax.sql.DataSource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 默认的事务管理器
 *
 * @author tiansheng
 */
public class DefaultTransaction implements Transaction, InvocationHandler {

  private DataSource dataSource;
  private TransactionIsolationLevel level;
  private Connection connection; // 当前使用的连接

  private static final Log LOG = LogFactory.getLog(DefaultTransaction.class);

  @Override
  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public Connection getConnection() throws SQLException {
    return getConnection(true);
  }

  @Override
  public Connection getConnection(boolean openTransaction) throws SQLException {
    if (dataSource == null) {
      throw new SQLException("Current transaction manager not support create connection.");
    }
    connection = dataSource.getConnection();
    if (openTransaction) {
      return (Connection) Proxy.newProxyInstance(getClass().getClassLoader(), PooledConnection.IFACE, this);
    } else {
      connection.setAutoCommit(true);
      return connection;
    }
  }

  @Override
  public void setTransactionIsolationLevel(TransactionIsolationLevel level) {
    this.level = level;
  }

  @Override
  public TransactionIsolationLevel getTransactionIsolationLevel() {
    return level;
  }

  @Override
  public void commit() throws SQLException {
    if (connection != null && !connection.getAutoCommit()) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("An error occurred while commit the connection[" + connection.hashCode() + "].");
      }
      this.connection.commit();
    } else {
      if (LOG.isDebugEnabled()) {
        LOG.debug("An error occurred while commit the connection, but no rollback. " +
                "Cause: connection is null or autoCommit property is true of connection.");
      }
    }
  }

  @Override
  public void rollback() throws SQLException {
    if (connection != null && !connection.getAutoCommit()) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("executing commit rollback for connection[" + connection.hashCode() + "]");
      }
      this.connection.rollback();
    } else {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Do not execute rollback. Cause: connection is null or " +
                "autoCommit property is true of connection.");
      }
    }
  }

  public void close() throws SQLException {
    if (connection != null) {
      connection.close();
    }
  }

  private void configurationConnection(Connection connection) throws SQLException {
    setDesiredAutoCommit(connection);
    if(level != null) {
      connection.setTransactionIsolation(level.getLevel());
    }
  }

  private void setDesiredAutoCommit(Connection connection) throws SQLException {
    if (connection.getAutoCommit()) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("set " + connection.hashCode() + " connection autoCommit property to true");
      }
      connection.setAutoCommit(false);
    }
  }

  /**
   * 检测连接是否出现异常
   *
   * @param proxy
   * @param method
   * @param args
   * @return
   * @throws Throwable
   */
  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    Object object = null;
    try {
      object = method.invoke(connection, args);
    } catch (Exception e) {
      // 发生任何异常都进行回滚
      rollback();
      // 记录异常信息
      LOG.error("An error occurred while execute " + method.getName()
              + " method of connection. Cause: " + e.getMessage(), e);
      e.printStackTrace();
    }
    return object;
  }
}
