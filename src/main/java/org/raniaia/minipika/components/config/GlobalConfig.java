package org.raniaia.minipika.components.config;

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
 * Creates on 2020/2/13.
 */


import org.raniaia.minipika.BeansManager;
import org.raniaia.minipika.components.config.components.AppCfg;
import org.raniaia.minipika.components.config.components.JapConfig;
import org.raniaia.minipika.components.config.components.PropertiesConfig;
import org.raniaia.minipika.components.entity.core.mysql.EntityLoaderImpl;
import org.raniaia.minipika.framework.exception.ConfigException;
import org.raniaia.minipika.components.entity.EntityLoader;
import org.raniaia.minipika.framework.provide.component.Component;

/**
 * 统一配置调用
 * @author tiansheng
 * @since 1.8
 */
@Component
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
            EntityLoader init = BeansManager.newInstance(EntityLoaderImpl.class);
            init.run();
        }
        return config;
    }

    private String configPath;

    private GlobalConfig() {
        this("minipika.jap");
    }

    private GlobalConfig(String configpath) {
        this.configPath = configpath;
    }

    private AbstractConfig newConfig() {
        AbstractConfig config;
        String suffix = configPath.substring(configPath.lastIndexOf(".") + 1);
        switch (suffix) {
            case "cfg":
                config = new AppCfg(configPath);
                break;
            case "jap":
                config = new JapConfig(configPath);
                break;
            case "properties":
                config = new PropertiesConfig(configPath);
                break;
            default:
                throw new ConfigException("unknown config file suffix '" + suffix + "'");
        }
        return config;
    }

    public static boolean isJar() {
        return jar;
    }

    public static void setJar(boolean jar) {
        GlobalConfig.jar = jar;
    }
}
