package org.raniaia.poseidon.framework.config;

import org.raniaia.poseidon.Container;
import org.raniaia.poseidon.components.model.core.mysql.ModelLoaderImpl;
import org.raniaia.poseidon.framework.exception.runtime.ConfigException;
import org.raniaia.poseidon.components.model.ModelLoader;

/**
 * 统一配置调用
 * Copyright by tiansheng on 2020/2/13 3:28
 * @author tiansheng
 * @version 1.0.0
 * @since 1.8
 */
public final class GlobalConfig {

    private static boolean jar = false;

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
            ModelLoader init = Container.getContainer().newInstance(ModelLoaderImpl.class);
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

    public static boolean isJar() {
        return jar;
    }

    public static void setJar(boolean jar) {
        GlobalConfig.jar = jar;
    }
}
