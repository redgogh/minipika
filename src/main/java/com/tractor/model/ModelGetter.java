package com.tractor.model;

import com.tractor.annotation.*;
import com.tractor.exception.TractorException;
import com.tractor.tools.StringUtils;
import com.tractor.tools.TractorUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/12 10:19
 * @since 1.8
 */
public class ModelGetter {

    /**
     * 对Model进行解析
     *
     * @param target 目标实体类
     */
    public GlobalMsg enhance(Class<?> target) throws TractorException {
        String engine = null;                                   // 储存引擎
        String tableName = null;                                // 表名
        GlobalMsg globalMsg = new GlobalMsg();          // 完整sql
        StringBuilder script = new StringBuilder();             // sql代码
        Map<String, String> columns = new LinkedHashMap<>();    // 字段信息
        script.append("create table if not exists");
        // 判断实体类有没有Model注解
        Map<String, String> modelData = modelData(target, globalMsg);
        tableName = modelData.get("table");
        engine = modelData.get("engine");
        GlobalMsg.putModel(target);
        script.append(" `").append(tableName).append("`\n").append("(\n"); // 开头
        // 解析字段
        field(target, globalMsg, script, columns);
        // 结尾
        if (script.charAt(script.length() - 2) == ',') {
            script.deleteCharAt(script.length() - 2);
        }
        script.append(") ENGINE = ".concat(String.valueOf(engine)).concat("\n"));
        script.append("\tDEFAULT CHARACTER SET = utf8\n");
        script.append("\tCOLLATE = utf8_general_ci\n");
        script.append("\tAUTO_INCREMENT = 1;");
        globalMsg.setCreateTableSql(script.toString());
        globalMsg.setColumns(columns);
        return globalMsg;
    }

    /**
     * Model注解解析操作
     *
     * @param target
     * @param globalMsg
     * @return
     * @throws TractorException
     */
    public Map<String, String> modelData(Class<?> target, GlobalMsg globalMsg) throws TractorException {
        String tableName = "";
        Map<String, String> map = new HashMap<>();
        if (SecurityManager.existModel(target)) {
            Model model = TractorUtils.getModelAnnotation(target);
            tableName = model.value();
            Engine engine = model.engine();
            if (StringUtils.isEmpty(tableName)) {
                throw new TractorException("@Model value cannot null");
            }
            globalMsg.setTableName(tableName);
            globalMsg.setEngine(engine);
            map.put("table", tableName);
            map.put("engine", String.valueOf(engine));
        }
        return map;
    }

    /**
     * 解析字段
     *
     * @param target
     * @param globalMsg
     * @throws TractorException
     */
    public void field(Class<?> target, GlobalMsg globalMsg, StringBuilder script, Map<String, String> columns) throws TractorException {
        String primaryKey = "";
        Field[] fields = target.getDeclaredFields();
        for (Field field : fields) {
            String columnName = TractorUtils.humpToUnderline(field.getName());
            StringBuilder tableColumn = new StringBuilder(columnName); // 字段
            tableColumn.insert(0, "`").append("`");
            // 判断该字段是否被忽略
            if (field.isAnnotationPresent(Ignore.class)) continue;
            // 字段声明
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getDeclaredAnnotation(Column.class);
                String statement = column.value();
                if (StringUtils.isEmpty(statement)) {
                    throw new TractorException("column property statement cannot null");
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
                    tableColumn.append("auto_increment");
                }
                primaryKey = "\tPRIMARY KEY (`" + columnName + "`),\n";
                globalMsg.setPrimaryKey(columnName);
            }
            // 注释
            if (field.isAnnotationPresent(Comment.class)) {
                Comment comment = field.getDeclaredAnnotation(Comment.class);
                tableColumn.append(" ").append("comment '").append(comment.value()).append("'");
            }
            script.append("\t").append(tableColumn).append(",\n");
            columns.put(columnName, tableColumn.toString());
        }
        script.append(primaryKey);
    }

    /**
     * 解析
     *
     * @param list
     */
    public void parse(List<Class<?>> list) {
        try {
            for (Class<?> target : list) {
                GlobalMsg globalMsg = enhance(target);
                GlobalMsg.putAttribute(globalMsg.getTableName(), globalMsg);
            }
        } catch (TractorException e) {
            e.printStackTrace();
        }
        System.out.println();
    }

}
