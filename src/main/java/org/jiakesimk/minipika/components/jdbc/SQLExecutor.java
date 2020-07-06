package org.jiakesimk.minipika.components.jdbc;

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
 * Creates on 2020/6/18.
 */

import lombok.Getter;
import org.jiakesimk.minipika.framework.factory.Factorys;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 * 动态SQL执行器
 *
 * @author tiansheng
 */
public class SQLExecutor implements Executor {

  @Getter
  private final NativeJdbc nativeJdbc = Factorys.forClass(NativeJdbc.class);

  @Override
  public <T> T queryForObject(String sql, Class<T> obj, Object... args) throws SQLException {
    NativeResultSet resultSet = nativeJdbc.select(sql, args);
    if (resultSet != null) {
      return resultSet.conversionJavaBean(obj);
    }
    return null;
  }

  @Override
  public <T> List<T> queryForList(String sql, Class<T> obj, Object... args) throws SQLException {
    NativeResultSet resultSet = nativeJdbc.select(sql, args);
    if (resultSet != null) {
      return resultSet.conversionJavaList(obj);
    }
    return null;
  }

  @Override
  public String queryForJson(String sql, Object... args) throws SQLException {
    NativeResultSet resultSet = nativeJdbc.select(sql, args);
    if (resultSet != null) {
      return resultSet.toJSONString();
    }
    return null;
  }

  @Override
  public NativeResultSet queryForNativeResult(String sql, Object... args) throws SQLException {
    return nativeJdbc.select(sql, args);
  }

  @Override
  public int update(String sql, Object... args) throws SQLException {
    return nativeJdbc.update(sql, args);
  }

  @Override
  public int insert(String sql, Object... args) throws SQLException {
    return nativeJdbc.update(sql, args);
  }

  @Override
  public int count(String sql, Object... args) throws SQLException {
    StringBuilder value = new StringBuilder(sql.toLowerCase());
    String select = "select";
    int selectPos = value.indexOf(select) + select.length();
    int fromPos = value.indexOf("from");
    value.replace(selectPos, fromPos, " count(*) ");
    NativeResultSet result = nativeJdbc.select(value.toString(), args);
    result.hasNext();
    String next = result.next();
    return Integer.parseInt(next == null ? "0" : next);
  }

  @Override
  public int update(String sql, Object... args) throws SQLException {
    return nativeJdbc.update(sql, args);
  }

  @Override
  public boolean execute(String sql, Object... args) throws SQLException {
    return nativeJdbc.execute(sql, args);
  }

  @Override
  public int[] batch(String sql, List<Object[]> args) throws SQLException {
    return nativeJdbc.executeBatch(sql, args);
  }

  @Override
  public int[] batch(String sql, Object[] args) throws SQLException {
    return nativeJdbc.executeBatch(sql, args);
  }

  @Override
  public int[] batch(String[] sql, List<Object[]> args) throws SQLException {
    return nativeJdbc.executeBatch(sql, args);
  }

}
