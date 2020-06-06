package org.raniaia.minipika.components.jdbc;

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

import org.raniaia.minipika.components.jdbc.datasource.DataSourceManager;
import org.raniaia.minipika.components.jdbc.transaction.Transaction;
import org.raniaia.minipika.framework.factory.Factorys;
import org.raniaia.minipika.framework.logging.Log;
import org.raniaia.minipika.framework.logging.LogFactory;
import org.raniaia.minipika.framework.util.ArrayUtils;
import org.raniaia.minipika.framework.util.SQLUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tiansheng
 */
public class DefaultSQLExecutor implements SQLExecutor {

  private static final Log LOG = LogFactory.getLog(DefaultSQLExecutor.class);

  @Override
  public boolean execute(String sql, Object... args) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("execute: " + sql);
    }
    Connection connection = null;
    PreparedStatement statement = null;
    Transaction transaction = getTransaction();
    try {
      connection = transaction.getConnection();
      statement = connection.prepareStatement(sql);
      boolean bool = setValues(statement, args).execute();
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
  public QueryResultSet select(String sql, Object... args) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("query: " + sql + ", current database: " + "");
    }
    QueryResultSet result = null;
    Connection connection = null;
    PreparedStatement statement = null;
    Transaction transaction = getTransaction();
    try {
      connection = transaction.getConnection();
      statement = connection.prepareStatement(sql);
      ResultSet resultSet = setValues(statement, args).executeQuery();
      return Factorys.forClass(QueryResultSet.class).build(resultSet);
    } catch (Throwable e) {
      e.printStackTrace();
    } finally {
      close(statement);
      transaction.close();
    }
    return null;
  }

  @Override
  public int update(String sql, Object... args) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("NativeJdbc: 执行SQL - " + sql);
    }
    Connection connection = null;
    PreparedStatement statement = null;
    Transaction transaction = getTransaction();
    try {
      connection = transaction.getConnection();
      statement = connection.prepareStatement(sql);
      int result = setValues(statement, args).executeUpdate();
      transaction.commit(); // 提交
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
  public int[] batch(String sql, List<Object[]> args) {
    // 判断sql中是否包含多条sql，根据';'来判断
    out:
    if (sql.contains(";")) {
      String[] sqls = (String[]) ArrayUtils.remove(sql.split(";"), ArrayUtils.OP.LAST);
      // 如果sql包含';'，但是数组中只有一条sql的话就跳出if
      if (sqls.length == 1) {
        break out;
      }
      List<Object[]> objList = new ArrayList<>();
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
      return batch(sqls, objList);
    }
    Connection connection = null;
    PreparedStatement statement = null;
    Transaction transaction = getTransaction();
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
  public int[] batch(String[] sqls, List<Object[]> args) {
    Statement statement = null;
    Connection connection = null;
    Transaction transaction = getTransaction();
    try {
      connection = transaction.getConnection();
      statement = connection.createStatement();
      int index = -1;
      for (String sql : sqls) {
        statement.addBatch(SQLUtils.buildPreSQL(sql, args.get(index = (index + 1))));
      }
      int[] result = statement.executeBatch();
      transaction.commit();
      return result;
    } catch (Throwable e) {
      transaction.rollback();
      e.printStackTrace();
    } finally {
      close(statement);
    }
    return new int[0];
  }

  private Transaction getTransaction() {
    DataSource dataSource = DataSourceManager.getDataSource();
    Transaction transaction = Factorys.forClass(Transaction.class);
    transaction.setDataSource(dataSource);
    return transaction;
  }

}
