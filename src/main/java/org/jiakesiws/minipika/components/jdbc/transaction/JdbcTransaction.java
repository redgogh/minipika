package org.jiakesiws.minipika.components.jdbc.transaction;

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

import org.jiakesiws.minipika.components.jdbc.datasource.pooled.PooledConnection;
import org.jiakesiws.minipika.framework.logging.Log;
import org.jiakesiws.minipika.framework.logging.LogFactory;

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
public class JdbcTransaction implements Transaction, InvocationHandler {

  private DataSource dataSource;
  private TransactionIsolationLevel level;
  private Connection connection; // 当前使用的连接

  private static final Log LOG = LogFactory.getLog(JdbcTransaction.class);

  @Override
  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public Connection getConnection() {
    return getConnection(true);
  }

  @Override
  public Connection getConnection(boolean openTransaction) {
    try {
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
    } catch (SQLException e) {
      LOG.error("Error getConnection failure, Cause: " + e.getMessage());
      e.printStackTrace();
    }
    return null;
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
  public void commit() {
    try {
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
    } catch (SQLException e) {
      LOG.error("Error execute commit failure, Cause: " + e.getMessage());
      e.printStackTrace();
    }
  }

  @Override
  public void rollback() {
    try {
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
    } catch (SQLException e) {
      LOG.error("Error rollback failure, Cause: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public void close() {
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
    }
  }

  private void configurationConnection(Connection connection) {
    setDesiredAutoCommit(connection);
    if (level != null) {
      try {
        connection.setTransactionIsolation(level.getLevel());
      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
    }
  }

  private void setDesiredAutoCommit(Connection connection) {
    try {
      if (connection.getAutoCommit()) {
        if (LOG.isDebugEnabled()) {
          LOG.debug("set " + connection.hashCode() + " connection autoCommit property to true");
        }
        connection.setAutoCommit(false);
      }
    } catch (SQLException throwables) {
      throwables.printStackTrace();
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
