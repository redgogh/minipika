package org.jiakesimk.minipika.components.entity.core.mysql;

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
 * Creates on 2019/11/12.
 */

import org.jiakesimk.minipika.framework.provide.component.Component;
import org.jiakesimk.minipika.framework.provide.entity.*;
import org.jiakesimk.minipika.framework.exception.MinipikaException;
import org.jiakesimk.minipika.components.entity.EntityParser;
import org.jiakesimk.minipika.components.entity.publics.Metadata;
import org.jiakesimk.minipika.framework.tools.EntityUtils;
import org.jiakesimk.minipika.framework.tools.StringUtils;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Parse annotation in entity, and generator database table.
 *
 * @author tiansheng
 */
@Component
public class EntityParserImpl implements EntityParser {

    /**
     * 解析类的信息，构建出一个实体的元数据
     */
    private Metadata buildMetadata(Class<?> target) throws MinipikaException {
        String engine = null;                                   // 储存引擎
        String tableName = null;                                // 表名
        Metadata metadata = new Metadata();                     // 完整sql
        StringBuilder script = new StringBuilder();             // sql代码
        Map<String, String> columns = new LinkedHashMap<>();    // 字段信息
        script.append("create table if not exists");
        // 判断实体类有没有Entity注解
        Map<String, String> entityData = parseEntityAnnotation(target, metadata);
        tableName = entityData.get("table");
        engine = entityData.get("engine");
        Metadata.putEntity(target);
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
        script.append("\tAUTO_INCREMENT = ").append(entityData.get("increment")).append(";");
        metadata.setCreateTableSql(script.toString());
        metadata.setColumns(columns);
        return metadata;
    }

    /**
     * 解析字段信息
     */
    private void parseField(Class<?> target, Metadata metadata, StringBuilder script, Map<String, String> columns) throws MinipikaException {
        for (Field field : EntityUtils.getEntityField(target)) {
            String columnName = EntityUtils.humpToUnderline(field.getName());
            StringBuilder tableColumn = new StringBuilder(columnName); // 字段
            tableColumn.insert(0, "`").append("`");
            // 判断该字段是否被忽略
            if (field.isAnnotationPresent(Ignore.class)) continue;
            // 字段声明
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getDeclaredAnnotation(Column.class);
                String statement = column.value();
                if (StringUtils.isEmpty(statement)) {
                    throw new MinipikaException("column property statement cannot null");
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
                if (!target.isAnnotationPresent(Entity.class))
                    continue;
                Metadata metadata = buildMetadata(target);
                Metadata.putAttribute(metadata.getTableName(), metadata);
            }
        } catch (MinipikaException e) {
            e.printStackTrace();
        }
    }

}
