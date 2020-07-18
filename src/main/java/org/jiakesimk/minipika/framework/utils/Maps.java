package org.jiakesimk.minipika.framework.utils;

import com.alibaba.fastjson.JSON;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 静态的Map工具类，注意如果在创建Map的时候要初始化
 * Map的大小，那么你设置的Map大小必须是2的幂。
 *
 * @author lts
 */
public final class Maps {

  private Maps() {
  }

  /**
   * 创建一个新的{@code HashMap}实例
   *
   * @return 新的且空的{@code HashMap}实例
   */
  public static <K, V> HashMap<K, V> newHashMap() {
    return new HashMap<>();
  }

  /**
   * 创建一个新的{@code HashMap}实例并设置{@code HashMap}的初始化大小，
   * 减少Map扩容带来的性能损耗。
   *
   * @return 新的且空的{@code HashMap}实例
   */
  public static <K, V> HashMap<K, V> newHashMap(int capacity) {
    return new HashMap<>(capacity);
  }

  /**
   * 拷贝{@code Map}中的数据到新创建的{@code HashMap}实例中
   *
   * @return 拷贝数据后的{@code HashMap}实例
   */
  public static <K, V> HashMap<K, V> newHashMap(Map<K, V> map) {
    return new HashMap<>(map);
  }

  /**
   * 创建一个新的{@code LinkedHashMap}实例
   *
   * @return 一个新的、空的{@code LinkedHashMap}实例
   */
  public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
    return new LinkedHashMap<>();
  }

  /**
   * 创建一个新的{@code LinkedHashMap}实例并设置{@code LinkedHashMap}的初始化大小，
   * 减少Map扩容带来的性能损耗。
   *
   * @return 一个新的、空的{@code LinkedHashMap}实例
   */
  public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(int capacity) {
    return new LinkedHashMap<>(capacity);
  }

  /**
   * 拷贝{@code Map}中的数据到新创建的{@code LinkedHashMap}中
   *
   * @return 拷贝数据后的{@code HashMap}实例
   */
  public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(Map<K, V> map) {
    return new LinkedHashMap<>(map);
  }

  /**
   * 创建一个新的{@link TreeMap}
   *
   * @return 一个新的、空的{@code TreeMap}实例
   */
  public static <K, V> TreeMap<K, V> newTreeMap() {
    return new TreeMap<>();
  }

  /**
   * 创建一个新的{@link TreeMap}
   *
   * @return 一个新的、空的{@code TreeMap}实例
   */
  public static <K, V> TreeMap<K, V> newTreeMap(Map<K,V> map) {
    return new TreeMap<>(map);
  }

  /**
   * 创建一个新的{@code ConcurrentHashMap}实例
   *
   * @return 一个新的、空的{@code ConcurrentHashMap}实例
   */
  public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap() {
    return new ConcurrentHashMap<>();
  }

  /**
   * 创建一个新的{@code ConcurrentHashMap}实例并设置{@code ConcurrentHashMap}的初始化大小，
   * 减少Map扩容带来的性能损耗。
   *
   * @return 一个新的、空的{@code ConcurrentHashMap}实例
   */
  public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap(int capacity) {
    return new ConcurrentHashMap<>(capacity);
  }

  /**
   * 拷贝{@code Map}中的数据到新的{@code ConcurrentHashMap}实例中
   *
   * @return 拷贝数据后的{@code ConcurrentHashMap}实例
   */
  public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap(Map<K, V> map) {
    return new ConcurrentHashMap<>(map);
  }

  /**
   * 获取{@code Map}中的第一个Key
   */
  public static <K, V> K getFirstKey(Map<K, V> map) {
    for (K key : map.keySet()) {
      return key;
    }
    return null;
  }

  /**
   * 获取{@code Map}中的第一个值
   */
  public static <K, V> V getFirstValue(Map<K, V> map) {
    for (V value : map.values()) {
      return value;
    }
    return null;
  }

  /**
   * 删除{@code Map}中的第一条数据，返回Key
   */
  public static <K, V> K removeFirstKey(Map<K, V> map) {
    for (K key : map.keySet()) {
      map.remove(key);
      return key;
    }
    return null;
  }

  /**
   * 删除{@code Map}中的第一条数据，返回Value
   */
  public static <K, V> V removeFirstValue(Map<K, V> map) {
    for (Map.Entry<K, V> entry : map.entrySet()) {
      return map.remove(entry.getKey());
    }
    return null;
  }

  /**
   * 根据下标获取{@code Map}中的Key
   */
  public static <K, V> K getKey(Map<K, V> map, int index) {
    int count = 0;
    for (K key : map.keySet()) {
      if (count == index) return key;
      count++;
    }
    return null;
  }

  /**
   * 根据下标获取{@code Map}中的Value
   */
  public static <K, V> V getValue(Map<K, V> map, int index) {
    int count = 0;
    for (V value : map.values()) {
      if (count == index) return value;
      count++;
    }
    return null;
  }

  public static <K, V> String toString(Map<K, V> map) {
    return JSON.toJSONString(map);
  }

}
