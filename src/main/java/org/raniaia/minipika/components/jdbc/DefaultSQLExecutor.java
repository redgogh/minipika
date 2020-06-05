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

import java.util.List;

/**
 * @author tiansheng
 */
public class DefaultSQLExecutor implements SQLExecutor {

  @Override
  public boolean execute(String sql, Object... args) {
    return false;
  }

  @Override
  public QueryResultSet select(String sql, Object... args) {
    return null;
  }

  @Override
  public int update(String sql, Object... args) {
    return 0;
  }

  @Override
  public int insert(String sql, Object... args) {
    return 0;
  }

  @Override
  public int delete(String sql, Object... args) {
    return 0;
  }

  @Override
  public int[] batch(String sql, List<Object[]> args) {
    return new int[0];
  }

  @Override
  public int[] batch(String[] sql, List<Object[]> args) {
    return new int[0];
  }

}
