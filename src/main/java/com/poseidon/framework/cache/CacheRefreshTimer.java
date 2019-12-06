package com.poseidon.framework.cache;

import com.poseidon.framework.config.Config;
import com.poseidon.framework.timer.Timer;

/**
 * Create by 2BKeyboard on 2019/12/7 0:47
 */
public class CacheRefreshTimer implements Timer {

    @Override
    public void run() {

    }

    @Override
    public long time() {
        return Config.getInstance().getRefresh();
    }
}
