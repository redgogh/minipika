package org.raniaia.minipika.components.config.components;

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

import org.raniaia.minipika.components.config.AbstractConfig;
import org.raniaia.minipika.framework.jap.JapLoader;

import java.util.Map;

/**
 * jap config配置文件，全称是Json and properties
 * 这个配置文件由当前框架所创造的。
 *
 * jap config file full name is Json and properties.
 * this config file create by minipika.
 *
 * @author tiansheng
 */
public class JapConfig extends AbstractConfig {

    private Map<String, String> config = null;

    private static JapConfig japConfig;

    public static JapConfig getJapConfig() {
        if (japConfig == null) {
            japConfig = new JapConfig();
        }
        return japConfig;
    }

    public JapConfig() {
        super();
    }

    public JapConfig(String path) {
        super(new JapLoader().load(path).get("minipika"));
    }

}
