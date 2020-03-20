package org.raniaia.poseidon.framework.tools;

import java.util.HashMap;
import java.util.Map;

/**
 * 静态{@link Map}工具类。
 *
 * Static utility methods to {@link Map} instance.
 *
 * Copyright: Create by tiansheng on 2020/1/19 18:30
 */
public class Maps {

    private Maps(){}

    /**
     * Creates <i>mutable</i>, empty {@code HashMap} instance.
     *
     * @return a new, empty {@code HashMap}
     */
    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<>();
    }

    /**
     * Creates <i>mutable</i>, empty {@code HashMap} instance.
     *
     * Copy a {@link Map} instance to created {@code HashMap} instance.
     *
     * @return a new {@code HashMap} instance after copy.
     */
    public static <K, V> HashMap<K, V> newHashMap(Map<K, V> map) {
        return new HashMap<>(map);
    }

    /**
     * Get first key in {@link Map} instance.
     *
     * @return first key in map.
     */
    public static Object getFirstKey(Map map) {
        for (Object o : map.keySet()) {
            return o;
        }
        return null;
    }

    /**
     * Get first value in {@link Map} instance.
     *
     * @return first value in {@link Map} instance.
     */
    public static Object getFirstValue(Map map) {
        for (Object value : map.values()) {
            return value;
        }
        return null;
    }

    /**
     * Get key by index in {@link Map} instance.
     *
     * @return the index key.
     */
    public static Object getKey(Map map, int index) {
        int count = 0;
        for (Object value : map.keySet()) {
            if (count == index) return value;
            count++;
        }
        return null;
    }

    /**
     * Get value by index in {@link Map} instance.
     *
     * @return the index value.
     */
    public static Object getValue(Map map, int index) {
        int count = 0;
        for (Object value : map.values()) {
            if (count == index) return value;
            count++;
        }
        return null;
    }

}
