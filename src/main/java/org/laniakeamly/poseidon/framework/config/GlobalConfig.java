package org.laniakeamly.poseidon.framework.config;

import org.laniakeamly.poseidon.framework.exception.runtime.ConfigException;
import org.laniakeamly.poseidon.framework.model.LoaderModel;

/**
 * 统一配置调用
 * Copyright by TianSheng on 2020/2/13 3:28
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 */
public final class GlobalConfig {

    static {
        // 当配置文件第一次加载时初始化
        LoaderModel init = new LoaderModel();
        init.run();
    }

    private static AbstractConfig config;

    public static AbstractConfig getConfig() {
        if (config == null) {
            config = new GlobalConfig().newConfig();
        }
        return config;
    }

    private String configpath;

    public GlobalConfig() {
        this("poseidon.jap");
    }

    public GlobalConfig(String configpath) {
        this.configpath = configpath;
    }

    private AbstractConfig newConfig() {
        Object config = null;
        String suffix = configpath.substring(configpath.lastIndexOf(".") + 1);
        if ("jap".equals(suffix)) {
            config = new JapConfig(configpath);
        } else if ("properties".equals(suffix)) {
            config = new PropertiesConfig(configpath);
        } else {
            throw new ConfigException("unknown config file suffix '" + suffix + "'");
        }
        return (AbstractConfig) config;
    }

}
