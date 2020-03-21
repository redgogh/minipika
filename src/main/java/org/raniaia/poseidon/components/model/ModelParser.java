package org.raniaia.poseidon.components.model;

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
 * Create on 2020/3/20 0:13.
 */

import org.raniaia.poseidon.framework.provide.model.*;
import org.raniaia.poseidon.framework.exception.PoseidonException;
import org.raniaia.poseidon.components.model.publics.Metadata;
import org.raniaia.poseidon.framework.tools.ModelUtils;
import org.raniaia.poseidon.framework.tools.SecurityManager;
import org.raniaia.poseidon.framework.tools.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Model parser, extends {@code BaseModuleAdapter}
 * @author tiansheng
 */
public interface ModelParser {

    /**
     * Provided to external use the model parser.
     */
    void parse(List<Class<?>> list);

    /**
     * Parse model annotation.
     */
    default Map<String, String> parseModelAnnotation(Class<?> target, Metadata metadata) throws PoseidonException {
        String tableName;
        Map<String, String> map = new HashMap<>();
        if (SecurityManager.existModel(target)) {
            Model model = ModelUtils.getModelAnnotation(target);
            tableName = model.value();
            Engine engine = model.engine();
            if (StringUtils.isEmpty(tableName)) {
                throw new PoseidonException("@Model value cannot null");
            }
            metadata.setTableName(tableName);
            metadata.setEngine(engine);
            map.put("table", tableName);
            map.put("engine", String.valueOf(engine));
            map.put("increment", String.valueOf(model.increment()));
        }
        return map;
    }

}
