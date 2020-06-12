package org.raniaia.minipika.components.cache;

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

import org.raniaia.minipika.components.jdbc.QueryResultSet;
import org.raniaia.minipika.framework.util.Captcha;
import org.raniaia.minipika.framework.util.Eac64;

/**
 * @author tiansheng
 */
public interface ResultSetCache {

  /**
   * 获取缓存中的{@link QueryResultSet}对象
   *
   * @param sql sql语句
   * @param args sql语句参数
   * @return {@link QueryResultSet}对象
   */
  QueryResultSet getResultSet(String sql, Object... args);

  /**
   * 添加{@link QueryResultSet}对象, 同时更新也通过
   * 这个方法去更新, 覆盖掉原有的QueryResultSet对象
   *
   * @param sql sql语句
   * @param args sql语句参数
   * @param resultSet 缓存对象
   */
  void addResultSet(String sql, QueryResultSet resultSet, Object... args);

  /**
   * 清空所有缓存对象
   */
  void refreshAll();

  /**
   * 设置定时清空缓存时间
   * @param millis 毫秒
   */
  void setClearTime(long millis);

  /**
   * 生成唯一key, 根据sql语句和参数去生成
   * @param sql sql语句
   * @param args 参数
   * @return md5 key
   */
  default String getKey(String sql, Object... args) {
    StringBuilder key = new StringBuilder(sql);
    for (Object arg : args) {
      key.append(arg.toString());
    }
    return Eac64.MD5.digest32(key.toString());
  }

}
