package org.raniaia.poseidon.components.cache;

import org.raniaia.poseidon.components.config.GlobalConfig;
import org.raniaia.poseidon.framework.timer.Timer;

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
 * Copyright: Create by tiansheng on 2019/12/7 0:47
 *
 * @author tiansheng
 * @version 1.0.0
 * @since 1.8
 *
 */
public class CacheRefreshTimer implements Timer {

    private PoseidonCache cache;

    public CacheRefreshTimer(PoseidonCache cache){
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
