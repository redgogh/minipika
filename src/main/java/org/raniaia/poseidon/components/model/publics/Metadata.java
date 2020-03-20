package org.raniaia.poseidon.components.model.publics;

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
 * Creates on 2019/11/12 11:01
 */

import org.raniaia.poseidon.framework.provide.model.Engine;
import org.raniaia.poseidon.framework.provide.model.Model;
import org.raniaia.poseidon.framework.tools.SecurityManager;
import org.raniaia.poseidon.components.model.database.ColumnPo;
import org.raniaia.poseidon.framework.tools.ModelUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data table metadata object.
 * @author tiansheng
 */
public class Metadata {

    private static final Map<String, Metadata> messages = new HashMap<>();

    /**
     * 模型的类对象
     */
    private static final Map<String, Class<?>> modelClass = new HashMap<>();

    /**
     * 表名对应类名
     */
    private static final Map<String, String> modelNameAndTableName = new HashMap<>();

    /**
     * 每张表对应的字段属性
     */
    private static final Map<String, List<ColumnPo>> db_columns = new HashMap<>();

    /**
     * 主键字段
     */
    private String primaryKey;

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
     * 所有Model对象的存放路径
     */
    private Map<String, String> modelClassPath;

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

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public static Map<String, Class<?>> getModelClass() {
        return modelClass;
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

    public static void putModel(Class<?> target) {
        if (SecurityManager.existModel(target)) {
            String simpleName = target.getSimpleName();
            Model model = ModelUtils.getModelAnnotation(target);
            modelClass.put(simpleName, target);
            modelNameAndTableName.put(model.value(),simpleName);
        }
    }

    public static Class<?> getModelClass(String tableName) {
        return modelClass.get(tableName);
    }

    public static String getModelSimpleNameByTable(String table) {
        return modelNameAndTableName.get(table);
    }

    public static void putDbColumn(String tableName,List<ColumnPo> columnModels){
        db_columns.put(tableName,columnModels);
    }

    public static List<ColumnPo> getDbColumn(String tableName){
        return db_columns.get(tableName);
    }

}
