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
 * Creates on 2020/2/13.
 */

import org.raniaia.approve.framework.exception.ConfigException;
import org.raniaia.approve.framework.tools.StringUtils;

/**
 * @author tiansheng
 * @since 1.8
 */
interface ApproveConfig {

    enum DriverType {MYSQL, ORACLE}

    void prefix(String prefix);
    void cache(boolean desired);
    void cache(boolean desired,Integer refresh);
    void cache(boolean desired,String refresh);
    void transaction(boolean desired);
    void maximumIdleConnection(int size);
    void minimumIdleConnection(int size);
    void model(String... packages);
    void mapper(String... paths);
    void norm(String path);
    void defaultData(String path);
    void datasource(String url,String driver,String username,String password);

}
