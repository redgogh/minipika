package org.jiakesimk.minipika.components.jdbc;

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

import org.jiakesimk.minipika.components.cache.Cache;
import org.jiakesimk.minipika.components.configuration.XMLConfig;
import org.jiakesimk.minipika.components.jdbc.datasource.DataSourceManager;
import org.jiakesimk.minipika.components.jdbc.datasource.pooled.PooledDataSource;
import org.jiakesimk.minipika.components.jdbc.datasource.pooled.PooledState;
import org.jiakesimk.minipika.components.jdbc.transaction.Transaction;
import org.jiakesimk.minipika.components.logging.Log;
import org.jiakesimk.minipika.components.logging.LogFactory;
import org.jiakesimk.minipika.framework.annotations.Component;
import org.jiakesimk.minipika.framework.common.ProxyHandler;
import org.jiakesimk.minipika.framework.factory.Factorys;
import org.jiakesimk.minipika.framework.utils.*;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.*;
import java.util.List;

/**
 * @author 2B键盘
 * @email jiakesiws@gmail.com
 */

public class NativeJdbcImpl implements NativeJdbc, ProxyHandler {

  private Transaction transaction;

  @Component
  private Cache cache;

  @Component
  private XMLConfig config;

  /**
   * 开启缓存
   */
  private boolean opencache = true;

  private static final Log LOG = LogFactory.getLog(NativeJdbcImpl.class);

  public NativeJdbcImpl() {
    String opencache = System.getProperty("opencache");
    if (StringUtils.isNotEmpty(opencache)) {
      this.opencache = Boolean.valueOf(opencache);
    }
  }

  @Override
  public boolean execute(String sql, Object... args) throws SQLException {
    if (LOG.isDebugEnabled()) {
      LOG.debug("execute sql - " + sql);
    }
    Connection connection = null;
    PreparedStatement statement = null;
    transaction = getTransaction();
    try {
      connection = transaction.getConnection();
      statement = connection.prepareStatement(sql);
      setValues(statement, args).execute();
      transaction.commit(); // 提交
      return true;
    } catch (Exception e) {
      transaction.rollback();
      LOG.error(e.getMessage(), e);
      return false;
    } finally {
      AutoClose.close(statement);
      transaction.close();
    }
  }

  @Override
  public NativeResultSet select(String sql, Object... args) throws SQLException {
    if (LOG.isDebugEnabled()) LOG.debug("execute sql - " + sql);
    NativeResultSet nResultSet = null;
    if (opencache) {
      nResultSet = cache.fetch(Cache.genKey(sql, args));
      if (nResultSet != null) {
        return nResultSet;
      }
    }
    Connection connection = null;
    PreparedStatement statement = null;
    try {
      transaction = getTransaction();
      connection = transaction.getConnection();
      statement = connection.prepareStatement(sql);
      ResultSet resultSet = setValues(statement, args).executeQuery();
      return cache.set(Cache.genKey(sql, args), Factorys.forClass(NativeResultSet.class).build(resultSet));
    } catch (Throwable e) {
      LOG.error(e.getMessage(), e);
      throw new SQLException(e);
    } finally {
      AutoClose.close(statement);
      transaction.close();
    }
  }

  @Override
  public int update(String sql, Object... args) throws SQLException {
    if (LOG.isDebugEnabled()) {
      LOG.debug("execute sql - " + sql);
    }
    Connection connection = null;
    PreparedStatement statement = null;
    try {
      if (opencache) {
        cache.update(sql);
      }
      transaction = getTransaction();
      connection = transaction.getConnection();
      statement = connection.prepareStatement(sql);
      int result = setValues(statement, args).executeUpdate();
      transaction.commit(); // 提交
      return result;
    } catch (Throwable e) {
      transaction.rollback(); // 回滚
      LOG.error(e.getMessage(), e);
      throw new SQLException(e);
    } finally {
      AutoClose.close(statement);
      transaction.close();
    }
  }

  @Override
  public int[] executeBatch(String sql, List<Object[]> args) throws SQLException {
    return this.executeBatch(sql, args.toArray());
  }

  @Override
  public int[] executeBatch(String sql, Object... args) throws SQLException {
    if (LOG.isDebugEnabled()) {
      LOG.debug("execute sql - " + sql);
    }
    Connection connection = null;
    PreparedStatement statement = null;
    try {
      transaction = getTransaction();
      int[] result = isMultiSQL(sql, args);
      if (result != null) {
        return result;
      } else {
        if (opencache) {
          cache.update(sql);
        }
      }
      connection = transaction.getConnection();
      statement = connection.prepareStatement(sql);
      for (Object arg : args) {
        Object[] value = (Object[]) arg;
        int i = 1;
        for (Object o : value) {
          statement.setObject(i, o);
          i++;
        }
        statement.addBatch();
      }
      result = statement.executeBatch();
      transaction.commit();
      return result;
    } catch (Throwable e) {
      transaction.rollback();
      LOG.error(e.getMessage(), e);
      throw new SQLException(e);
    } finally {
      AutoClose.close(statement);
    }
  }

  @Override
  public int[] executeBatch(String[] sql, List<Object[]> args) throws SQLException {
    if (LOG.isDebugEnabled()) {
      for (String it : sql) {
        LOG.debug("execute sql - " + it);
      }
    }
    for (String s : sql) {
      if (opencache) {
        cache.update(s);
      }
    }
    Connection connection = null;
    Statement statement = null;
    try {
      transaction = getTransaction();
      connection = transaction.getConnection();
      statement = connection.createStatement();
      int index = -1;
      for (String it : sql) {
        if (args != null) {
          statement.addBatch(SQLUtils.buildPreSQL(it, args.get(index = (index + 1))));
        } else {
          statement.addBatch(SQLUtils.buildPreSQL(it, null));
        }
      }
      int[] result = statement.executeBatch();
      transaction.commit();
      return result;
    } catch (Throwable e) {
      transaction.rollback();
      LOG.error(e.getMessage(), e);
      throw new SQLException(e);
    } finally {
      AutoClose.close(statement);
    }
  }

  /**
   * @return 当前事务管理器
   */
  private Transaction getTransaction() throws SQLException {
    DataSource dataSource = DataSourceManager.getDataSource();
    if (dataSource == null) {
      throw new SQLException("Error get transaction failure. Cause: not obtained DataSource.");
    }
    Transaction transaction = Factorys.forClass(Transaction.class);
    transaction.setDataSource(dataSource);
    return transaction;
  }

  // 判断sql中是否包含多条sql，根据';'来判断
  private int[] isMultiSQL(String sql, Object... args) throws SQLException {
    out:
    if (sql.contains(";")) {
      String[] sqls = (String[]) Arrays.remove(sql.split(";"), Arrays.OP.LAST);
      // 如果sql包含';'，但是数组中只有一条sql的话就跳出if
      if (sqls.length == 1) break out;
      List<Object[]> objList = Lists.newArrayList();
      int argsIndex = 0;
      for (String isql : sqls) {
        int length = 0;
        for (char chara : isql.toCharArray()) {
          if (chara == '?') length++;
        }
        Object[] objects = new Object[length];
        System.arraycopy(args, argsIndex, objects, 0, length);
        argsIndex = length;
        objList.add(objects);
      }
      return executeBatch(sqls, objList);
    }
    return null;
  }

  // 添加预编译sql的参数
  private PreparedStatement setValues(PreparedStatement statement, Object... args) throws SQLException {
    if (args != null) {
      for (int i = 0; i < args.length; i++) {
        statement.setObject((i + 1), args[i]);
      }
    }
    return statement;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T getProxyHandler() {
    return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(),
            new Class[]{NativeJdbc.class},
            this);
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    long startTime = System.currentTimeMillis();
    Object result = method.invoke(this, args);
    long endTime = System.currentTimeMillis() - startTime;

    String sql = ((String) args[0]);
    PooledDataSource dataSource = (PooledDataSource) transaction.getDataSource();
    PooledState state = dataSource.getState();
    state.setSqlTimeConsuming(sql, endTime);
    state.addFrequency(sql);

    return result;
  }

}
