package org.jiakesiws.minipika.components.cache;

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
 * Creates on 2019/11/13.
 */

import org.jiakesiws.minipika.components.jdbc.NativeResultSet;
import org.jiakesiws.minipika.framework.utils.Maps;
import org.jiakesiws.minipika.framework.utils.SQLUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author lts
 * @email jiakesiws@gmail.com
 */
public class FetchCache implements Cache {

  private final Map<String, NativeResultSet> map = Maps.newConcurrentHashMap();

  private final ReentrantReadWriteLock readAndWriteLock = new ReentrantReadWriteLock();

  @Override
  public NativeResultSet set(String key, NativeResultSet resultSet) {
    readAndWriteLock.writeLock().lock();
    try {
      map.put(key, resultSet);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      readAndWriteLock.writeLock().unlock();
    }
    return resultSet;
  }

  @Override
  public NativeResultSet fetch(String key) {
    return map.get(key);
  }

  @Override
  public void update(String sql) {
    Map<String, Set<String>> keys = Cache.KEYS;
    List<String> tables = SQLUtils.getSQLTables(sql);
    for (String table : tables) {
      if(keys.containsKey(table)) {
        Set<String> setKeys = keys.get(table);
        for (String key : setKeys) {
          refresh(key);
        }
      }
    }
  }

  @Override
  public void refresh() {
    map.clear();
  }

  @Override
  public void refresh(String key) {
    readAndWriteLock.writeLock().lock();
    try {
      map.remove(key);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      readAndWriteLock.writeLock().unlock();
    }
  }

}
