package org.jiakesiws.minipika.components.jdbc;

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
 * Creates on 2019/11/30.
 */

import lombok.SneakyThrows;
import org.jiakesiws.minipika.BeansManager;
import org.jiakesiws.minipika.components.cache.MinipikaCache;
import org.jiakesiws.minipika.components.config.GlobalConfig;
import org.jiakesiws.minipika.components.jdbc.transaction.Transaction;
import org.jiakesiws.minipika.components.jdbc.transaction.TransactionFactory;
import org.jiakesiws.minipika.components.jdbc.transaction.TransactionIsolationLevel;
import org.jiakesiws.minipika.components.logging.Log;
import org.jiakesiws.minipika.components.logging.LogFactory;
import org.jiakesiws.minipika.framework.provide.Minipika;
import org.jiakesiws.minipika.framework.provide.component.Component;
import org.jiakesiws.minipika.framework.tools.Arrays;
import org.jiakesiws.minipika.framework.tools.SQLUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC操作
 *
 * @author 2B键盘
 * @email jiakesiws@gmail.com
 */
@Component
@SuppressWarnings("SpellCheckingInspection")
public class NativeJdbcImpl implements NativeJdbc {


  @Inject
  private MinipikaCache cache;

  @Inject
  private DataSource dataSource;

  @Inject
  protected TransactionFactory transactionFactory;

  private Log log = LogFactory.getLog(NativeJdbcImpl.class);

  protected final boolean isCache = GlobalConfig.getConfig().getCache();
  protected final boolean desiredAutoCommit = GlobalConfig.getConfig().gettransaction();

  public NativeJdbcImpl() {
  }

  /**
   * 这个构造器是提供给Minipika注解使用的，因为此注解的设计是可以通过构造器去初始化需要注入的对象。
   * 所以为NativeJdbcImpl提供了一个初始初始化的方法。
   *
   * @param transactionFactory {@code TransactionFactory} instance.
   * @see Minipika#paramsId
   */
  public NativeJdbcImpl(TransactionFactory transactionFactory) {
    this.transactionFactory = transactionFactory;
  }

  @Override
  public void setTransactionFactory(TransactionFactory transactionFactory) {
    this.transactionFactory = transactionFactory;
  }

  @Override
  public void setTransactionFactory(TransactionFactory transaction, TransactionIsolationLevel level) {
    this.transactionFactory = transactionFactory;
    this.transactionFactory.setTransactionIsolationLevel(level);
  }

  @Override
  public void setTransactionIsolationLevel(TransactionIsolationLevel level) {
    this.transactionFactory.setTransactionIsolationLevel(level);
  }

  @Override
  @SneakyThrows
  public boolean execute(String sql, Object... args) {
    if (log.isDebugEnabled()) {
      log.debug("execute: " + sql);
    }
    Connection connection = null;
    PreparedStatement statement = null;
    Transaction transaction = transactionFactory.newTransaction(dataSource, desiredAutoCommit);
    try {
      connection = transaction.getConnection();
      statement = connection.prepareStatement(sql);
      Boolean bool = setValues(statement, args).execute();
      transaction.commit(); // 提交
      return bool;
    } catch (Exception e) {
      transaction.rollback();
      e.printStackTrace();
    } finally {
      close(statement);
      transaction.close();
    }
    return false;
  }

  @Override
  @SneakyThrows
  public NativeResult executeQuery(String sql, Object... args) {
    if (log.isDebugEnabled()) {
      log.debug("query: " + sql + ", current database: " + GlobalConfig.getConfig().getDbname());
    }
    NativeResult result = null;
    Connection connection = null;
    PreparedStatement statement = null;
    Transaction transaction = transactionFactory.newTransaction(dataSource, desiredAutoCommit);
    try {
      connection = transaction.getConnection();
      statement = connection.prepareStatement(sql);
      // 判断是否开启缓存
      if (isCache) {
        result = cache.get(sql, args);
        if (result == null) {
          ResultSet resultSet = setValues(statement, args).executeQuery();
          result = BeansManager.newNativeResult().build(resultSet);
          cache.save(sql, result, args);
          return cache.get(sql, args);
        }
        return result;
      } else {
        ResultSet resultSet = setValues(statement, args).executeQuery();
        return BeansManager.newNativeResult().build(resultSet);
      }
    } catch (Throwable e) {
      e.printStackTrace();
    } finally {
      close(statement);
      transaction.close();
    }
    return null;
  }

  @Override
  @SneakyThrows
  public int executeUpdate(String sql, Object... args) {
    if (log.isDebugEnabled()) {
      log.debug("NativeJdbc: 执行SQL - " + sql);
    }
    Connection connection = null;
    PreparedStatement statement = null;
    Transaction transaction = transactionFactory.newTransaction(dataSource, desiredAutoCommit);
    try {
      connection = transaction.getConnection();
      statement = connection.prepareStatement(sql);
      int result = setValues(statement, args).executeUpdate();
      transaction.commit(); // 提交
      if (isCache) cache.refresh(sql); // 刷新缓存
      return result;
    } catch (Throwable e) {
      transaction.rollback(); // 回滚
      e.printStackTrace();
    } finally {
      close(statement);
      transaction.close();
    }
    return 0;
  }

  @Override
  public int[] executeBatch(String sql, List<Object[]> args) {
    return this.executeBatch(sql, args.toArray());
  }

  @Override
  @SneakyThrows
  public int[] executeBatch(String sql, Object... args) {
    // 判断sql中是否包含多条sql，根据';'来判断
    out:
    if (sql.contains(";")) {
      String[] sqls = (String[]) Arrays.remove(sql.split(";"), Arrays.Op.LAST);
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
    Connection connection = null;
    PreparedStatement statement = null;
    Transaction transaction = transactionFactory.newTransaction(dataSource, desiredAutoCommit);
    try {
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
      int[] result = statement.executeBatch();
      transaction.commit();
      if (isCache) cache.refresh(sql);
      return result;
    } catch (Throwable e) {
      transaction.rollback();
      e.printStackTrace();
    } finally {
      close(statement);
    }
    return new int[0];
  }

  @Override
  @SneakyThrows
  public int[] executeBatch(String[] sqls, List<Object[]> args) {
    Statement statement = null;
    Connection connection = null;
    Transaction transaction = transactionFactory.newTransaction(dataSource, desiredAutoCommit);
    try {
      connection = transaction.getConnection();
      statement = connection.createStatement();
      int index = -1;
      for (String sql : sqls) {
        statement.addBatch(SQLUtils.buildPreSQL(sql, args.get(index = (index + 1))));
      }
      int[] result = statement.executeBatch();
      transaction.commit();
      if (isCache) {
        for (String sql : sqls) cache.refresh(sql);
      }
      return result;
    } catch (Throwable e) {
      transaction.rollback();
      e.printStackTrace();
    } finally {
      close(statement);
    }
    return new int[0];
  }

}
