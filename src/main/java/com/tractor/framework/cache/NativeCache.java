package com.tractor.framework.cache;

import com.tractor.framework.db.NativeResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Create by 2BKeyboard on 2019/11/29 17:16
 */
public class NativeCache {

    private static NativeCache instance;
    private Map<String, NativeResult> cache = new ConcurrentHashMap<>();

    public static NativeCache getCache() {
        if (instance == null) instance = new NativeCache();
        return instance;
    }

    public void save(String key, NativeResult value) {
        cache.put(key, value);
    }

    public NativeResult get(String key) {
        if (!cache.containsKey(key)) return null;
        return cache.get(key);
    }

    public boolean containsKey(String sql) {
        return cache.containsKey(sql);
    }

    public NativeResult remove(String key){
        return cache.remove(key);
    }

}
