package com.tengu.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/12 11:01
 * @since 1.8
 */
public class ModelMessage {

    private static final Map<String,ModelMessage> messages = new HashMap<>();

    /**
     * 主键字段
     */
    private String primaryKey;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 索引字段
     */
    private String[] indexes;

    /**
     * 创建表的SQL语句
     */
    private String createTableSql;

    public static void setMessages(String key,ModelMessage value){
        messages.put(key,value);
    }

    public static Map<String, ModelMessage> getMessages() {
        return messages;
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

    public String[] getIndexes() {
        return indexes;
    }

    public void setIndexes(String[] indexes) {
        this.indexes = indexes;
    }

    public String getCreateTableSql() {
        return createTableSql;
    }

    public void setCreateTableSql(String createTableSql) {
        this.createTableSql = createTableSql;
    }
}
