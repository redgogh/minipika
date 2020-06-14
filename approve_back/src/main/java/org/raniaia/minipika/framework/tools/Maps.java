package org.jiakesimk.minipika.framework.tools;

/*
 * Copyright (C) 2020 Tiansheng All rights reserved.
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
 * Creates on 2020/3/11.
 */

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * Static {@code Map} tools class.
 *
 * @author TianSheng
 */
public final class Maps {

    private Maps() {}

    /**
     * 创建一个新的{@code HashMap}实例
     * @return 新的且空的{@code HashMap}实例
     */
    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<>();
    }

    /**
     * 拷贝{@code Map}中的数据到新创建的{@code HashMap}实例中
     * @return 拷贝数据后的{@code HashMap}实例
     */
    public static <K, V> HashMap<K, V> newHashMap(Map<K, V> map) {
        return new HashMap<>(map);
    }

    /**
     * 创建一个新的{@code LinkedHashMap}实例
     * @return 一个新的、空的{@code LinkedHashMap}实例
     */
    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
        return new LinkedHashMap<>();
    }

    /**
     * 拷贝{@code Map}中的数据到新创建的{@code LinkedHashMap}中
     * @return 拷贝数据后的{@code HashMap}实例
     */
    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(Map<K, V> map) {
        return new LinkedHashMap<>(map);
    }

    /**
     * 创建一个新的{@code ConcurrentHashMap}实例
     * @return 一个新的、空的{@code ConcurrentHashMap}实例
     */
    public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap() {
        return new ConcurrentHashMap<>();
    }

    /**
     * 拷贝{@code Map}中的数据到新的{@code ConcurrentHashMap}实例中
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
    public static <K,V> K removeFirstKey(Map<K,V> map){
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

    public static String toString(Map map){
        return JSON.toJSONString(map);
    }

}
