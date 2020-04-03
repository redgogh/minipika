package org.raniaia.approve.components.config;

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
 * Creates on 2019/12/3.
 */

import org.raniaia.approve.framework.tools.StringUtils;
import org.raniaia.available.io.Paths;

import java.net.URL;

/**
 * 手动加载配置文件
 *
 * manual loader config file.
 *
 * @author tiansheng
 * @version 1.0.0
 * @since 1.8
 */
public class ConfigLoader {

    private ConfigLoader() {
    }

    /**
     * Loading config.
     * @param configPath
     */
    public static void loadConfig(String configPath) {
        URL url = Thread.currentThread().getStackTrace()[2].getClass().getResource("");
        if (StringUtils.isEmpty(configPath)) {
            throw new NullPointerException("config path cannot null");
        }
        if (url != null && "jar".equals(url.getProtocol())) {
            GlobalConfig.setJar(true);
        }
        GlobalConfig.loaderConfig(Paths.toClasspath(configPath));
    }

}
