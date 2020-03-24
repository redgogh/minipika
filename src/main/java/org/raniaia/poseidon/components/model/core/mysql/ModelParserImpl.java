package org.raniaia.poseidon.components.model.core.mysql;

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
 * Creates on 2019/11/12.
 */

import org.raniaia.poseidon.framework.provide.component.Component;
import org.raniaia.poseidon.framework.provide.model.*;
import org.raniaia.poseidon.framework.exception.PoseidonException;
import org.raniaia.poseidon.components.model.ModelParser;
import org.raniaia.poseidon.components.model.publics.Metadata;
import org.raniaia.poseidon.framework.tools.ModelUtils;
import org.raniaia.poseidon.framework.tools.StringUtils;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Parse annotation in model, and generator database table.
 *
 * @author tiansheng
 */
@Component
public class ModelParserImpl implements ModelParser {

    /**
     * 解析类的信息，构建出一个实体的元数据
     */
    private Metadata buildMetadata(Class<?> target) throws PoseidonException {
        String engine = null;                                   // 储存引擎
        String tableName = null;                                // 表名
        Metadata metadata = new Metadata();                     // 完整sql
        StringBuilder script = new StringBuilder();             // sql代码
        Map<String, String> columns = new LinkedHashMap<>();    // 字段信息
        script.append("create table if not exists");
        // 判断实体类有没有Model注解
        Map<String, String> modelData = parseModelAnnotation(target, metadata);
        tableName = modelData.get("table");
        engine = modelData.get("engine");
        Metadata.putModel(target);
        script.append(" `").append(tableName).append("`\n").append("(\n"); // 开头
        // 解析字段
        parseField(target, metadata, script, columns);
        // 结尾
        if (script.charAt(script.length() - 2) == ',') {
            script.deleteCharAt(script.length() - 2);
        }
        script.append(") ENGINE = ".concat(String.valueOf(engine)).concat("\n"));
        script.append("\tDEFAULT CHARACTER SET = utf8\n");
        script.append("\tCOLLATE = utf8_general_ci\n");
        script.append("\tAUTO_INCREMENT = ").append(modelData.get("increment")).append(";");
        metadata.setCreateTableSql(script.toString());
        metadata.setColumns(columns);
        return metadata;
    }

    /**
     * 解析字段信息
     */
    private void parseField(Class<?> target, Metadata metadata, StringBuilder script, Map<String, String> columns) throws PoseidonException {
        for (Field field : ModelUtils.getModelField(target)) {
            String columnName = ModelUtils.humpToUnderline(field.getName());
            StringBuilder tableColumn = new StringBuilder(columnName); // 字段
            tableColumn.insert(0, "`").append("`");
            // 判断该字段是否被忽略
            if (field.isAnnotationPresent(Ignore.class)) continue;
            // 字段声明
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getDeclaredAnnotation(Column.class);
                String statement = column.value();
                if (StringUtils.isEmpty(statement)) {
                    throw new PoseidonException("column property statement cannot null");
                }
                tableColumn.append(" ").append(statement).append(" ");
            }
            // 自增长
            if (field.isAnnotationPresent(Increase.class)) {
                tableColumn.append("auto_increment");
            }
            // 主键
            if (field.isAnnotationPresent(PK.class)) {
                PK key = field.getDeclaredAnnotation(PK.class);
                if (key.increase()) {
                    tableColumn.append("auto_increment primary key");
                }
                metadata.setPk(columnName);
            }
            // 注释
            if (field.isAnnotationPresent(Comment.class)) {
                Comment comment = field.getDeclaredAnnotation(Comment.class);
                tableColumn.append(" ").append("comment '").append(comment.value()).append("'");
            }
            script.append("\t").append(tableColumn).append(",\n");
            columns.put(columnName, tableColumn.toString());
        }
    }

    @Override
    public void parse(List<Class<?>> list) {
        try {
            if (list == null) return;
            for (Class<?> target : list) {
                if (!target.isAnnotationPresent(Model.class))
                    continue;
                Metadata metadata = buildMetadata(target);
                Metadata.putAttribute(metadata.getTableName(), metadata);
            }
        } catch (PoseidonException e) {
            e.printStackTrace();
        }
    }

}
