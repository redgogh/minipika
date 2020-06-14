package org.jiakesimk.minipika.components.cache

import org.jiakesimk.minipika.components.jdbc.QueryResultSet

interface SetCache {

  /**
   * 获取缓存中的{@link org.jiakesimk.minipika.components.jdbc.QueryResultSet}对象
   *
   * @param key 缓存键
   * @return {@link org.jiakesimk.minipika.components.jdbc.QueryResultSet}对象
   */
  QueryResultSet getResultSet(String key)

  /**
   * 添加{@link QueryResultSet}对象, 同时更新也通过
   * 这个方法去更新, 覆盖掉原有的QueryResultSet对象
   *
   * @param key 缓存键
   * @param resultSet 缓存对象
   */
  void addResultSet(String key, QueryResultSet resultSet)

  /**
   * 清空所有缓存对象
   */
  void refreshAll()

  /**
   * 设置定时清空缓存时间
   * @param millis 毫秒
   */
  void setClearTime(long millis)

}