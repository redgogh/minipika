package com.tengu.model;

import com.tengu.annotation.*;
import com.tengu.annotation.Index;
import com.tengu.exception.ParseException;
import com.tengu.exception.TenguException;
import com.tengu.tools.StringUtils;
import com.tengu.tools.TenguUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/12 10:19
 * @since 1.8
 */
public class ParseModel {

    /**
     * 对Model进行解析
     *
     * @param target 目标实体类
     */
    public ModelAttribute enhance(Class<?> target) throws TenguException {
        String tableName = null;                                // 表名
        ModelAttribute message = new ModelAttribute();          // 完整sql
        StringBuilder script = new StringBuilder();             // sql代码
        Map<String, String> columns = new LinkedHashMap<>();    // 字段信息
        script.append("create table if not exists");
        // 判断实体类有没有Model注解
        tableName = modelData(target, message);
        ModelAttribute.putModel(target);
        script.append(" `").append(tableName).append("`\n").append("(\n"); // 开头
        // 解析字段
        field(target, message, script, columns);
        // 结尾
        if (script.charAt(script.length() - 2) == ',') {
            script.deleteCharAt(script.length() - 2);
        }
        script.append(") ENGINE = InnoDB\n");
        script.append("\tDEFAULT CHARACTER SET = utf8\n");
        script.append("\tCOLLATE = utf8_general_ci\n");
        script.append("\tAUTO_INCREMENT = 1;");
        message.setCreateTableSql(script.toString());
        message.setColumns(columns);
        return message;
    }

    /**
     * Model注解解析操作
     *
     * @param target
     * @param message
     * @return
     * @throws ParseException
     */
    public String modelData(Class<?> target, ModelAttribute message) throws ParseException {
        String tableName = "";
        if (target.isAnnotationPresent(Model.class)) {
            Model model = target.getDeclaredAnnotation(Model.class);
            tableName = model.value();
            if (StringUtils.isEmpty(tableName)) {
                throw new ParseException("Model表名不能为空");
            }
            message.setTableName(tableName);
        }
        // 判断是否有索引注解
        if (target.isAnnotationPresent(Indexes.class)) {
            Index[] indices = target.getDeclaredAnnotation(Indexes.class).value();
            for (Index index : indices) {
                IndexAttribute ia = new IndexAttribute();
                ia.setColumns(index.columns());
                ia.setAlias(index.alias());
                ia.setType(index.type());
                ia.setComment(index.comment());
                ia.buildScript(tableName);
                message.addIndexes(ia);

            }
        }
        return tableName;
    }

    /**
     * 解析字段
     *
     * @param target
     * @param message
     * @throws ParseException
     */
    public void field(Class<?> target, ModelAttribute message, StringBuilder script, Map<String, String> columns) throws ParseException {
        String primaryKey = "";
        Field[] fields = target.getDeclaredFields();
        for (Field field : fields) {
            String columnName = TenguUtils.humpToUnderline(field.getName());
            StringBuilder tableColumn = new StringBuilder(columnName); // 字段
            tableColumn.insert(0, "`").append("`");
            // 判断该字段是否被忽略
            if (field.isAnnotationPresent(Ignore.class)) continue;
            // 字段声明
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getDeclaredAnnotation(Column.class);
                String statement = column.value();
                if (StringUtils.isEmpty(statement)) {
                    throw new ParseException("字段属性声明不能为空");
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
                message.setPrimaryKey(columnName);
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
                ModelAttribute message = enhance(target);
                ModelAttribute.putAttribute(message.getTableName(), message);
            }
        } catch (TenguException e) {
            e.printStackTrace();
        }
        System.out.println();
    }

}
