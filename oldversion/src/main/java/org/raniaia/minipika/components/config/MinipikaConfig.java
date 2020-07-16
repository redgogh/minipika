package org.jiakesimk.minipika.components.config;

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

import org.jiakesimk.minipika.components.jdbc.datasource.unpooled.Dsi;
import org.jiakesimk.minipika.framework.provide.Minipika;
import org.jiakesimk.minipika.framework.tools.Lists;
import org.jiakesimk.minipika.framework.tools.Maps;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MinipikaConfig {

    protected Map<String,String> config = Maps.newHashMap();

    /**
     * Configuration DataSource info.
     */
    protected
    void datasource(Dsi... dataSources)
    {

    }

    /**
     * Enable transaction.
     */
    protected
    void transaction(boolean desiredTransaction)
    {

    }

    /**
     * Enable cache.
     */
    protected
    void cache(boolean desiredCache)
    {

    }

    /**
     * Enable cache.
     */
    protected
    void cache(boolean desiredCache, long refresh)
    {

    }

    /**
     * Configuartion connection pool the idle connection size.
     *
     * @param maximum max size
     * @param minimum min size
     */
    protected
    void pool(long minimum,long maximum)
    {

    }

    /**
     * Configuration table name prefix.
     */
    protected
    void prefix(String prefix)
    {

    }

    /**
     * Configuration entity class path.
     */
    protected
    void entity(String... basePackages)
    {

    }

    /**
     * Configuration mapper file path.
     */
    protected
    void mapper(String... basePackages)
    {

    }

}
