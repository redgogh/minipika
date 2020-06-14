package org.jiakesimk.minipika.components.cache;

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
 * Creates on 2020/6/1.
 */

import org.jiakesimk.minipika.components.jdbc.QueryResultSet;
import org.jiakesimk.minipika.framework.util.Maps;

import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * @author tiansheng
 */
@SuppressWarnings("ConstantConditions")
public class WeakCacheImpl implements ResultSetCache {

  private final WeakReference<Map<String, QueryResultSet>> weak = new WeakReference<>(Maps.newHashMap());

  @Override
  public QueryResultSet getResultSet(String key) {
    return weak.get().get(key);
  }

  @Override
  public void addResultSet(String key, QueryResultSet resultSet) {
    weak.get().put(key, resultSet);
  }

  @Override
  public void refreshAll() {
    weak.get().clear();
  }

  @Override
  public void setClearTime(long millis) {
  }

}
