package org.laniakeamly.poseidon.framework.model;


import org.laniakeamly.poseidon.framework.exception.PoseidonException;
import org.laniakeamly.poseidon.framework.tools.StringUtils;
import org.laniakeamly.poseidon.framework.tools.PoseidonUtils;
import org.laniakeamly.poseidon.framework.limit.*;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author TianSheng
 * @version 1.0.0
 * @date 2019/11/12 10:19
 * @since 1.8
 */
public class GetterModel {

    /**
     * 对Model进行解析
     *
     * @param target 目标实体类
     */
    public Metadata enhance(Class<?> target) throws PoseidonException {
        String engine = null;                                   // 储存引擎
        String tableName = null;                                // 表名
        Metadata metadata = new Metadata();                     // 完整sql
        StringBuilder script = new StringBuilder();             // sql代码
        Map<String, String> columns = new LinkedHashMap<>();    // 字段信息
        script.append("create table if not exists");
        // 判断实体类有没有Model注解
        Map<String, String> modelData = modelData(target, metadata);
        tableName = modelData.get("table");
        engine = modelData.get("engine");
        Metadata.putModel(target);
        script.append(" `").append(tableName).append("`\n").append("(\n"); // 开头
        // 解析字段
        field(target, metadata, script, columns);
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
     * Model注解解析操作
     *
     * @param target
     * @param metadata
     * @return
     * @throws PoseidonException
     */
    public Map<String, String> modelData(Class<?> target, Metadata metadata) throws PoseidonException {
        String tableName = "";
        Map<String, String> map = new HashMap<>();
        if (SecurityManager.existModel(target)) {
            Model model = PoseidonUtils.getModelAnnotation(target);
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

    /**
     * 解析字段
     *
     * @param target
     * @param metadata
     * @throws PoseidonException
     */
    public void field(Class<?> target, Metadata metadata, StringBuilder script, Map<String, String> columns) throws PoseidonException {
        for (Field field : PoseidonUtils.getModelField(target)) {
            String columnName = PoseidonUtils.humpToUnderline(field.getName());
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
            if (field.isAnnotationPresent(PrimaryKey.class)) {
                PrimaryKey key = field.getDeclaredAnnotation(PrimaryKey.class);
                if (key.increase()) {
                    tableColumn.append("auto_increment primary key");
                }
                metadata.setPrimaryKey(columnName);
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

    /**
     * 解析
     *
     * @param list
     */
    public void parse(List<Class<?>> list) {
        try {
            for (Class<?> target : list) {
                Metadata metadata = enhance(target);
                Metadata.putAttribute(metadata.getTableName(), metadata);
            }
        } catch (PoseidonException e) {
            e.printStackTrace();
        }
    }

}
