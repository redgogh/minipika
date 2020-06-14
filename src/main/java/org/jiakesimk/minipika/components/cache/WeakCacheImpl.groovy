package org.jiakesimk.minipika.components.cache

import org.jiakesimk.minipika.components.jdbc.QueryResultSet
import org.jiakesimk.minipika.framework.util.Maps

import java.lang.ref.WeakReference

class WeakCacheImpl implements SetCache {
/**
 * 使用引用做缓存
 */
  private final WeakReference<Map<String, QueryResultSet>> weak = new WeakReference<>(Maps.newHashMap())

  private final Map<String, QueryResultSet> map = weak.get()

  @Override
  QueryResultSet getResultSet(String key) {
    return map.get(key)
  }

  @Override
  void addResultSet(String key, QueryResultSet resultSet) {
    map.put key, resultSet
  }

  @Override
  void refreshAll() {
    map.clear()
  }

  @Override
  void setClearTime(long millis) {

  }

}
