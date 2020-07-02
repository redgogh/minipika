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
  public <T> List<T> queryForList(String sql, Class<T> obj, Object... args) {
    return null;
  }

  @Override
  public <T> Set<T> queryForSet(String sql, Class<T> obj, Object... args) {
    return null;
  }

  @Override
  public String queryForJson(String sql, Object... args) {
    return null;
  }

  @Override
  public NativeResultSet queryForResult(String sql, Object... args) {
    return null;
  }

  @Override
  public int insert(String sql, Object... args) {
    return 0;
  }

  @Override
  public int count(String sql, Object... args) {
    return 0;
  }

  @Override
  public boolean execute(String sql, Object... args) {
    return false;
  }

  @Override
  public int[] batch(String sql, List<Object[]> args) {
    return new int[0];
  }

  @Override
  public int[] batch(String sql, Object[] args) {
    return new int[0];
  }

  @Override
  public int[] batch(String[] sql, List<Object[]> args) {
    return new int[0];
  }

}
