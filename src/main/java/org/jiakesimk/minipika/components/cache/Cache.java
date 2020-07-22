package org.jiakesimk.minipika.components.cache;

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
 * Creates on 2019/11/13.
 */

import org.jiakesimk.minipika.components.jdbc.NativeResultSet;
import org.jiakesimk.minipika.framework.utils.CEA64;
import org.jiakesimk.minipika.framework.utils.Maps;
import org.jiakesimk.minipika.framework.utils.SQLUtils;
import org.jiakesimk.minipika.framework.utils.Sets;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * {@link NativeResultSet}查询数据缓存
 *
 * @author lts
 * @email ltsloveyellow@aliyun.com
 */
public interface Cache {

  Map<String, Set<String>> KEYS = Maps.newConcurrentHashMap();

  /**
   * 存一条数据
   *
   * @param key       key值
   * @param resultSet 查询数据对象
   * @return resultSet
   */
  NativeResultSet set(String key, NativeResultSet resultSet);

  /**
   * 取出一条数据
   *
   * @param key 缓存对应的key
   * @return {@link NativeResultSet}实例对象
   */
  NativeResultSet fetch(String key);

  /**
   * 更新对应的表存在的缓存
   *
   * @param sql sql语句
   */
  void update(String sql);

  /**
   * 刷新所有缓存对象
   */
  void refresh();

  /**
   * 刷新单个缓存对象
   */
  void refresh(String key);

  /**
   * 根据sql和参数生成缓存对象的key值
   *
   * @param sql  sql语句
   * @param args 参数列表
   */
  static String genKey(String sql, Object... args) {
    // 获取SQL中存在的表，并生成MD5作为唯一标识
    List<String> tables = SQLUtils.getSQLTables(sql);
    // 生成缓存key
    StringBuilder keyBuilder = new StringBuilder(sql);
    if (args != null) {
      for (Object arg : args) {
        keyBuilder.append(arg);
      }
    }
    String key = CEA64.MD5.digest32(keyBuilder.toString());
    for (String table : tables) {
      if(KEYS.containsKey(table)) {
        Set<String> set = KEYS.get(table);
        if(!set.contains(table)) {
          set.add(key);
        }
      } else {
        KEYS.put(table, Sets.newHashSet());
        Set<String> set = KEYS.get(table);
        set.add(key);
      }
    }
    return key;
  }

}
