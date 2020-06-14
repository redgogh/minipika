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
 * Creates on 2019/12/7.
 */

import org.jiakesimk.minipika.components.config.GlobalConfig;
import org.jiakesimk.minipika.framework.timer.Timer;

/**
 *
 * Chinese:
 *
 * 刷新所有缓存定时器
 * 如果有些缓存只用一次就不再更新了，那么这个缓存就是在占用多余的内存。
 * 所以需要定时去刷新所有缓存，释放运行空间。
 *
 * English:
 *
 * refresh all timer.
 * if some cache just use once then this cache is taking up memory.
 * so we need timing refresh all cache freed runtime memory.
 *
 * @author tiansheng
 *
 */
public class CacheRefreshTimer implements Timer {

    private MinipikaCache cache;

    public CacheRefreshTimer(MinipikaCache cache){
        this.cache = cache;
    }

    @Override
    public void run() {
        cache.refreshAll();
    }

    @Override
    public long time() {
        return GlobalConfig.getConfig().getRefresh();
    }
}
