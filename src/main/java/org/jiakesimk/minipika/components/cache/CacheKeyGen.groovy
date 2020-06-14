package org.jiakesimk.minipika.components.cache

import org.jiakesimk.minipika.framework.util.Eac64

class CacheKeyGen {

  /**
   * 生成唯一key, 根据sql语句和参数去生成
   * @param sql sql语句
   * @param args 参数
   * @return md5 key
   */
  static String getKey(String sql, Object... args) {
    StringBuilder key = new StringBuilder(sql)
    for (Object arg : args) {
      key.append(arg.toString())
    }
    return Eac64.MD5.digest32(key.toString())
  }

}
