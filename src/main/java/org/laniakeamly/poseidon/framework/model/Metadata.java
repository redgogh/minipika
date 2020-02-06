package org.laniakeamly.poseidon.framework.model;

import org.laniakeamly.poseidon.framework.limit.Engine;
import org.laniakeamly.poseidon.framework.limit.Model;
import org.laniakeamly.poseidon.framework.tools.PoseidonUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author TianSheng
 * @version 1.0.0
 * @date 2019/11/12 11:01
 * @since 1.8
 */
public class Metadata {

    private static final Map<String, Metadata> messages = new HashMap<>();

    /**
     * 模型的类对象
     */
    private static final Map<String,Class<?>> modelClass = new HashMap<>();

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
    private Map<String,String> columns;

    public static void putAttribute(String key, Metadata value){
        messages.put(key,value);
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

    public static void putModel(Class<?> target){
        if(SecurityManager.existModel(target)){
            Model model = PoseidonUtils.getModelAnnotation(target);
            modelClass.put(model.value(),target);
        }
    }

    public static Class<?> getModelClass(String tableName){
        return modelClass.get(tableName);
    }

}
