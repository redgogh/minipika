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

    private static AbstractConfig config;

    private static boolean run = false;

    public static void loaderConfig(String path) {
        if (config == null) {
            config = new GlobalConfig(path).newConfig();
            // 初始化加载配置
            getConfig();
        }
    }

    public static AbstractConfig getConfig() {
        if (config == null) {
            config = new GlobalConfig().newConfig();
        }
        if (!run) {
            run = true;
            LoaderModel init = new LoaderModel();
            init.run();
        }
        return config;
    }

    private String configPath;

    private GlobalConfig() {
        this("poseidon.jap");
    }

    private GlobalConfig(String configpath) {
        this.configPath = configpath;
    }

    private AbstractConfig newConfig() {
        Object config;
        String suffix = configPath.substring(configPath.lastIndexOf(".") + 1);
        if ("jap".equals(suffix)) {
            config = new JapConfig(configPath);
        } else if ("properties".equals(suffix)) {
            config = new PropertiesConfig(configPath);
        } else {
            throw new ConfigException("unknown config file suffix '" + suffix + "'");
        }
        return (AbstractConfig) config;
    }

}
