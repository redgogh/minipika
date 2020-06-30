package org.jiakesimk.minipika.components.cache;

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
 * Creates on 2019/12/4.
 */

import org.jiakesimk.minipika.components.jdbc.NativeResult;
import org.jiakesimk.minipika.framework.provide.component.Component;
import org.jiakesimk.minipika.framework.tools.MinipikaUtils;
import org.jiakesimk.minipika.framework.tools.SQLUtils;

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
 * @author tiansheng
 * @version 1.0.0
 * @since 1.8
 *
 */
@Component
public class MinipikaCacheImpl implements MinipikaCache {

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
            }
            container.put(key, result);
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
            values.add(arg == null ? "NULL" : arg.toString());
        }
        return MinipikaUtils.encryptToMd5(values.toString());
    }

}
