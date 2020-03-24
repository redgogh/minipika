package org.raniaia.poseidon.components.config;

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

import org.raniaia.poseidon.framework.exception.runtime.ConfigException;
import org.raniaia.poseidon.framework.tools.StringUtils;

/**
 * @author tiansheng
 * @since 1.8
 */
interface PoseidonConfig {

    enum DriverType {MYSQL, ORACLE}

    /**
     * 加载驱动
     */
    default DriverType loadDriver(String classpath) {
        if (StringUtils.isEmpty(classpath)) {
            throw new ConfigException("jdbc driver cannot null.");
        }
        System.setProperty("jdbc.drivers", classpath);
        if (classpath.contains("mysql")) {
            return DriverType.MYSQL;
        } else if (classpath.contains("oracle")) {
            return DriverType.ORACLE;
        }
        return DriverType.MYSQL;
    }

    /**
     * get config value
     *
     * @param key
     * @return
     */
    String getValue(String key);

}
