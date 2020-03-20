package org.raniaia.poseidon.framework.cache;

import org.raniaia.poseidon.framework.db.NativeResult;
import org.raniaia.poseidon.framework.tools.POFUtils;
import org.raniaia.poseidon.framework.tools.SQLUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * 默认缓存实现类
 * 这个实现类使用Map存储数据，同时也是适用Map存储Key
 *
 * 为什么要用Map去存储{@link CacheKey}？
 *      因为在更新的时候我们需要获取key中的表，然后对对应的缓存进行刷新。
 *
 * English:
 *
 * Cache implements class.
 * this implements class use {@link Map} save data and use {@link Map} save key.
 *
 * why need {@link Map} save {@link CacheKey}?
 *      because in update we need get tables in {@link CacheKey} then
 *      refresh specify cache.
 *
 * Copyright: Create by tiansheng on 2019/12/4 20:25
 *
 * @author tiansheng
 * @version 1.0.0
 * @since 1.8
 *
 */
public class PoseidonCacheImpl implements PoseidonCache{

    private Map<String, CacheKey> keyMap = new HashMap<>();

    // 容器
    private Map<String, NativeResult> container = new ConcurrentHashMap<>();

    // 读写锁
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock writeLock = readWriteLock.writeLock();
    private final Lock readLock = readWriteLock.readLock();

    public NativeResult get(String sql, Object... args) {
        return container.get(getKey(sql, args));
    }

    public void save(String sql, NativeResult result, Object... args) {
        writeLock.lock();
        try {
            String key = getKey(sql, args);
            CacheKey cacheKey = keyMap.get(key);
            if (cacheKey == null) {
                cacheKey = new CacheKey();
                cacheKey.setKey(key);
                cacheKey.setTables(SQLUtils.getSQLTables(sql));
                keyMap.put(key, cacheKey);
                container.put(key, result);
            } else {
                container.put(key, result);
            }
        }catch (Throwable e){
            e.printStackTrace();
        }finally {
            writeLock.unlock();
        }
    }

    public void refresh(String sql) {
        List<String> tables = SQLUtils.getSQLTables(sql);
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

    /**
     * 刷新所有缓存
     */
    public void refreshAll() {
        writeLock.lock();
        keyMap.clear();
        container.clear();
        writeLock.unlock();
    }

    private String getKey(String sql, Object... args) {
        List<String> values = new ArrayList<>(5);
        values.add(sql);
        for (Object arg : args) {
            values.add(arg.toString());
        }
        return POFUtils.encryptToMd5(values.toString());
    }

}
