package org.jiakesimk.minipika.components.jdbc.datasource.pooled;

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

import lombok.Getter;
import lombok.Setter;
import org.jiakesimk.minipika.components.logging.Log;
import org.jiakesimk.minipika.components.logging.LogFactory;
import org.jiakesimk.minipika.framework.exception.MinipikaException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Pooled connection.
 *
 * @author tiansheng
 */
public class PooledConnection implements InvocationHandler {

  private static String CLOSE = "close";
  private static Class<?>[] IFACE = new Class[]{Connection.class};

  private final static Log log = LogFactory.getLog(PooledConnection.class);

  private boolean                       valid;

  @Getter
  private Connection                    realConnection;

  @Getter
  private PooledDataSource              dataSource;

  @Getter
  private Connection                    proxyConnection;

  @Getter
  @Setter
  private long                          createTimestamp;

  @Getter
  @Setter
  private long                          lastUsedTimestamp;

  public PooledConnection() {
  }

  public PooledConnection(Connection connection, PooledDataSource dataSource) {
    this.realConnection = connection;
    this.dataSource = dataSource;
    this.proxyConnection = (Connection)
            Proxy.newProxyInstance(Connection.class.getClassLoader(), IFACE, this);
    this.valid = true;
    this.createTimestamp = System.currentTimeMillis();
  }

  public long checkoutTimestamp() {
    return (System.currentTimeMillis() - createTimestamp) / 1000;
  }

  /**
   * Verify that the connection works.
   */
  // 验证链接是否可以正常使用
  public boolean isValid() throws SQLException {
    return valid && (realConnection != null) && !realConnection.isClosed();
  }

  /**
   * Invalidates the connection.
   */
  public void invalidate() throws SQLException {
    forceClose();
    valid = false;
  }

  /**
   * Check if connection is available.
   */
  private void checkConnection() throws SQLException {
    if (!isValid()) {
      throw new SQLException("Error accessing connection is invalid.");
    }
  }

  /**
   * Check connection is available.
   */
  public boolean ping() {
    return false;
  }

  /**
   * Close the proxy connection, this close is not a really closed.
   * Is returning to connection pool.
   */
  public void close() throws SQLException {
    proxyConnection.close();
  }

  /**
   * force close.
   */
  // 强制关闭链接
  public void forceClose() throws SQLException {
    realConnection.close();
  }

  public int getRealHasCode() {
    return realConnection == null ? -1 : realConnection.hashCode();
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
    String methodName = method.getName();
    if (CLOSE.equals(methodName)) {
      dataSource.pushConnection(this);
      return null;
    } else {
      try {
        if (!Object.class.equals(method.getDeclaringClass())) {
          checkConnection();
        }
        return method.invoke(realConnection, args);
      } catch (Exception e) {
        throw new MinipikaException(e);
      }
    }
  }

}
