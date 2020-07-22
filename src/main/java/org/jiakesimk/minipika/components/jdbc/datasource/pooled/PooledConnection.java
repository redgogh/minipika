package org.jiakesimk.minipika.components.jdbc.datasource.pooled;

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
 * Creates on 2020/6/1.
 */

import org.jiakesimk.minipika.framework.exception.MinipikaException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author tiansheng
 */
public class PooledConnection implements InvocationHandler {

  private static final String CLOSE = "close";
  public static final Class<?>[] IFACE = new Class[]{Connection.class};

  protected boolean             valid = true;
  protected PooledState         state;
  protected PooledDataSource    dataSource;
  protected Connection          proxyConnection;        // 代理链接
  protected Connection          realConnection;         // 真实链接
  protected long                lastUsedTimestamp;      // 上次使用时间

  public PooledConnection() {
  }

  public PooledConnection(Connection connection, PooledDataSource dataSource) {
    this.dataSource = dataSource;
    this.realConnection = connection;
    this.state = dataSource.state;
    this.proxyConnection = (Connection)
            Proxy.newProxyInstance(Connection.class.getClassLoader(), IFACE, this);
  }

  /**
   * 验证链接是否可以正常使用
   */
  public boolean isValid() throws SQLException {
    return this.valid && (realConnection != null) && !realConnection.isClosed();
  }

  /**
   * 使链接失效
   */
  public void invalidate() {
    this.valid = false;
  }

  /**
   * 归还链接
   */
  public void close() throws SQLException {
    dataSource.pushConnection(this);
  }

  /**
   * 强制关闭链接
   */
  void forceClose() throws SQLException {
    this.realConnection.close();
    state.currentConnectionsCount--;
  }

  /**
   * 获取真实链接
   */
  public Connection getRealConnection() {
    return this.realConnection;
  }

  public int getRealHasCode() {
    return realConnection.hashCode();
  }

  /**
   * 检查链接是否可用
   */
  public boolean ping() {
    return false;
  }

  /**
   * 设置链接上次使用时间
   *
   * @param lastUsedTimestamp 时间戳
   */
  public void setLastUsedTimestamp(long lastUsedTimestamp) {
    this.lastUsedTimestamp = lastUsedTimestamp;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    String methodName = method.getName();
    if (CLOSE.equals(methodName)) {
      dataSource.pushConnection(this);
      return null;
    } else {
      try {
        return method.invoke(realConnection, args);
      } catch (Exception e) {
        throw new MinipikaException(e);
      }
    }
  }

}
