package org.raniaia.approve.components.config;

/*
 * Copyright (C) 2020 Tiansheng All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"){}
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
public abstract class ApproveConfig {

    enum DriverType {MYSQL, ORACLE}

    protected void prefix(String prefix){}
    protected void cache(boolean desired){}
    protected void cache(boolean desired,Integer refresh){}
    protected void cache(boolean desired,String refresh){}
    protected void transaction(boolean desired){}
    protected void maximumIdleConnection(int size){}
    protected void minimumIdleConnection(int size){}
    protected void entity(String... packages){}
    protected void mapper(String... paths){}
    protected void norm(String path){}
    protected void defaultData(String path){}
    protected void datasource(String url,String driver,String username,String password){}

}
