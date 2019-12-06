package com.poseidon.framework.cache;

import com.poseidon.framework.db.NativeJdbc;
import com.poseidon.framework.db.NativeResult;
import com.poseidon.framework.tools.PoseidonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Create by 2BKeyboard on 2019/12/4 20:25
 */
public class PoseidonCache {

    private Map<String, CacheKey> keyMap = new HashMap<>();

    // 容器
    private Map<String, NativeResult> container = new ConcurrentHashMap<>();

    // 读写锁
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private Lock writeLock = readWriteLock.writeLock();
    private Lock readLock = readWriteLock.readLock();

    private final static PoseidonCache cache = new PoseidonCache();

    public static PoseidonCache getCache() {
        return cache;
    }

    /**
     * 获取缓存
     * @param sql
     * @param args
     * @return
     */
    public NativeResult get(String sql,Object... args){
        return container.get(getKey(sql, args));
    }

    /**
     * 保存
     * @param sql
     * @param result
     * @param args
     * @return
     */
    public NativeResult save(String sql,NativeResult result,Object... args){
        String key = getKey(sql, args);
        CacheKey cacheKey = keyMap.get(key);
        if (cacheKey == null) {
            cacheKey = new CacheKey();
            cacheKey.setKey(key);
            cacheKey.setTables(PoseidonUtils.getSQLTables(sql));
            keyMap.put(key,cacheKey);
            container.put(key,result);
            return container.get(key);
        }
        return container.put(key,result);
    }

    /**
     * 刷新缓存
     * @param sql
     */
    public void refresh(String sql) {
        List<String> tables = PoseidonUtils.getSQLTables(sql);
        if (!tables.isEmpty()) {
            List<String> keys = new ArrayList<>();
            for (Map.Entry<String, CacheKey> cacheKey : keyMap.entrySet()) {
                for (String table : tables) {
                    if (cacheKey.getValue().getTables().contains(table)) {
                        keys.add(cacheKey.getKey());
                    }
                }
            }
            for (String key : keys) {
                container.remove(key);
            }
        }
    }

    private String getKey(String sql, Object... args) {
        List<String> values = new ArrayList<>(5);
        values.add(sql);
        for (Object arg : args) {
            values.add(arg.toString());
        }
        return PoseidonUtils.encryptToMd5(values.toString());
    }

}
