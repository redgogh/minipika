package org.jiakesimk.minipika.components.entity;

/* ************************************************************************
 *
 * Copyright (C) 2020 tiansheng All rights reserved.
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
 *
 * ************************************************************************/

/*
 * Create on 2020/3/20 0:13.
 */

import org.jiakesimk.minipika.framework.provide.entity.*;
import org.jiakesimk.minipika.framework.exception.MinipikaException;
import org.jiakesimk.minipika.components.entity.publics.Metadata;
import org.jiakesimk.minipika.framework.tools.EntityUtils;
import org.jiakesimk.minipika.framework.tools.SecurityManager;
import org.jiakesimk.minipika.framework.tools.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Entity parser, extends {@code BaseModuleAdapter}
 * @author tiansheng
 * @email jiakesiws@gmail.com
 */
public interface EntityParser {

    /**
     * Provided to external use the entity parser.
     */
    void parse(List<Class<?>> list);

    /**
     * Parse entity annotation.
     */
    default Map<String, String> parseEntityAnnotation(Class<?> target, Metadata metadata) throws MinipikaException {
        String tableName;
        Map<String, String> map = new HashMap<>();
        if (SecurityManager.existEntity(target)) {
            Entity entity = EntityUtils.getEntityAnnotation(target);
            tableName = entity.value();
            Engine engine = entity.engine();
            if (StringUtils.isEmpty(tableName)) {
                throw new MinipikaException("@Entity value cannot null");
            }
            metadata.setTableName(tableName);
            metadata.setEngine(engine);
            map.put("table", tableName);
            map.put("engine", String.valueOf(engine));
            map.put("increment", String.valueOf(entity.increment()));
        }
        return map;
    }

}
