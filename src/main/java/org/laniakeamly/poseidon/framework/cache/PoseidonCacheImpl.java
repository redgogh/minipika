package org.laniakeamly.poseidon.framework.cache;

import org.laniakeamly.poseidon.framework.db.NativeResult;
import org.laniakeamly.poseidon.framework.tools.PoseidonUtils;

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
        String key = getKey(sql, args);
        CacheKey cacheKey = keyMap.get(key);
        if (cacheKey == null) {
            cacheKey = new CacheKey();
            cacheKey.setKey(key);
            cacheKey.setTables(PoseidonUtils.getSQLTables(sql));
            keyMap.put(key, cacheKey);
            container.put(key, result);
        } else {
            container.put(key, result);
        }
        writeLock.unlock();
    }


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
        return PoseidonUtils.encryptToMd5(values.toString());
    }

}
