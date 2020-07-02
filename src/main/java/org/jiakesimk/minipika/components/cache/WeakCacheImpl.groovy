package org.jiakesimk.minipika.components.cache

import org.jiakesimk.minipika.components.jdbc.NativeResultSet
import org.jiakesimk.minipika.framework.util.Maps

import java.lang.ref.WeakReference

class WeakCacheImpl implements SetCache {
/**
 * 使用引用做缓存
 */
  private final WeakReference<Map<String, NativeResultSet>> weak = new WeakReference<>(Maps.newHashMap())

  private final Map<String, NativeResultSet> map = weak.get()

  @Override
  NativeResultSet getResultSet(String key) {
    return map.get(key)
  }

  @Override
  void addResultSet(String key, NativeResultSet resultSet) {
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
