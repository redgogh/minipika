package org.raniaia.poseidon.framework.context;

/*
 * Copyright (C) 2020 tiansheng All rights reserved.
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
 * Creates on 2020/3/20 23:45
 */

import javassist.ClassPool;
import org.raniaia.poseidon.extension.ConnectionPool;
import org.raniaia.poseidon.framework.cache.CacheRefreshTimer;
import org.raniaia.poseidon.framework.cache.PoseidonCache;
import org.raniaia.poseidon.framework.cache.PoseidonCacheImpl;
import org.raniaia.poseidon.framework.db.JdbcSupport;
import org.raniaia.poseidon.framework.db.JdbcSupportImpl;
import org.raniaia.poseidon.framework.db.NativeJdbc;
import org.raniaia.poseidon.framework.db.NativeJdbcImpl;
import org.raniaia.poseidon.framework.loader.PoseidonClassPool;
import org.raniaia.poseidon.framework.provide.Resource;
import org.raniaia.poseidon.framework.timer.Timer;
import org.raniaia.poseidon.framework.timer.TimerManager;

/**
 * @author tiansheng
 */
public class MyBeans {

    @Resource
    private NativeJdbc newNativeJdbc() {
        return new NativeJdbcImpl();
    }

    @Resource(name = "jdbc")
    private JdbcSupport newJdbcSupport() {
        return new JdbcSupportImpl();
    }

    @Resource(name = "cache")
    private PoseidonCache newPoseidonCache() {
        PoseidonCache cache = new PoseidonCacheImpl();
        Timer timer = new CacheRefreshTimer(cache);
        TimerManager.getManager().submit(timer);
        return cache;
    }

    @Resource(name = "pool")
    private ConnectionPool newConnectionPool() {
        return new org.raniaia.poseidon.framework.pool.ConnectionPool();
    }

    @Resource(name = "classPool")
    private ClassPool getClassPool() throws ClassNotFoundException {
        PoseidonClassPool pool = new PoseidonClassPool(true);
        return pool;
    }

}
