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
 * Creates on 2019/11/13.
 */

import org.jiakesimk.minipika.components.jdbc.NativeResultSet;
import org.jiakesimk.minipika.framework.util.CEA64;

/**
 * {@link NativeResultSet}查询数据缓存
 *
 * @author lts
 */
public interface Cache {

  /**
   * 存一条数据
   *
   * @param key       key值
   * @param resultSet 查询数据对象
   */
  void set(String key, NativeResultSet resultSet);

  /**
   * 取出一条数据
   *
   * @param key 缓存对应的key
   * @return {@link NativeResultSet}实例对象
   */
  NativeResultSet fetch(String key);

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
    StringBuilder key = new StringBuilder(sql);
    if(args != null) {
      for (Object arg : args) {
        key.append(arg);
      }
    }
    return CEA64.MD5.digest32(key.toString());
  }

}
