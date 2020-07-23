package org.jiakesimk.minipika.components.entity.publics;

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

import org.jiakesimk.minipika.framework.provide.entity.Engine;
import org.jiakesimk.minipika.framework.provide.entity.Entity;
import org.jiakesimk.minipika.framework.tools.SecurityManager;
import org.jiakesimk.minipika.components.entity.database.ColumnEntity;
import org.jiakesimk.minipika.framework.tools.EntityUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data table metadata object.
 * @author tiansheng
 * @email jiakesiws@gmail.com
 */
public class Metadata {

    private static final Map<String, Metadata> messages = new HashMap<>();

    /**
     * 模型的类对象
     */
    private static final Map<String, Class<?>> entityClass = new HashMap<>();

    /**
     * 表名对应类名
     */
    private static final Map<String, String> entityNameAndTableName = new HashMap<>();

    /**
     * 每张表对应的字段属性
     */
    private static final Map<String, List<ColumnEntity>> db_columns = new HashMap<>();

    /**
     * 主键字段
     */
    private String pk;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 储存引擎，如：innodb
     */
    private Engine engine;

    /**
     * 创建表的SQL语句
     */
    private String createTableSql;

    /**
     * 字段
     */
    private Map<String, String> columns;

    /**
     * 所有Entity对象的存放路径
     */
    private Map<String, String> entityClassPath;

    public static void putAttribute(String key, Metadata value) {
        messages.put(key, value);
    }

    public static Map<String, Metadata> getAttribute() {
        return messages;
    }

    public Map<String, String> getColumns() {
        return columns;
    }

    public void setColumns(Map<String, String> columns) {
        this.columns = columns;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public static Map<String, Class<?>> getEntityClass() {
        return entityClass;
    }

    public String getCreateTableSql() {
        return createTableSql;
    }

    public void setCreateTableSql(String createTableSql) {
        this.createTableSql = createTableSql;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public static void putEntity(Class<?> target) {
        if (SecurityManager.existEntity(target)) {
            String simpleName = target.getSimpleName();
            Entity entity = EntityUtils.getEntityAnnotation(target);
            entityClass.put(simpleName, target);
            entityNameAndTableName.put(entity.value(),simpleName);
        }
    }

    public static Class<?> getEntityClass(String tableName) {
        return entityClass.get(tableName);
    }

    public static String getEntitySimpleNameByTable(String table) {
        return entityNameAndTableName.get(table);
    }

    public static void putDbColumn(String tableName,List<ColumnEntity> columnEntitys){
        db_columns.put(tableName,columnEntitys);
    }

    public static List<ColumnEntity> getDbColumn(String tableName){
        return db_columns.get(tableName);
    }

}
