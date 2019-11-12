package com.tengu.db.model;

import com.tengu.annotation.*;
import com.tengu.exception.ParseException;
import com.tengu.exception.TenguException;
import com.tengu.experiment.UserModel;
import com.tengu.tools.StringUtils;
import com.tengu.tools.TenguUtils;

import java.lang.reflect.Field;

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
     * @param target
     */
    public void enhance(Class<?> target) throws TenguException {
        String tableName = null; // 表名
        String primaryKey = null; // 主键
        StringBuilder script = new StringBuilder();
        script.append("create table ");
        // 判断实体类有没有Model注解
        if (target.isAnnotationPresent(Model.class)) {
            Model model = target.getDeclaredAnnotation(Model.class);
            tableName = model.value();
            if (StringUtils.isEmpty(tableName)) {
                throw new ParseException("Model表名不能为空");
            }
        }
        script.append("`").append(tableName).append("`\n").append("(\n"); // 开头
        // 解析字段
        Field[] fields = target.getDeclaredFields();
        for (Field field : fields) {
            String columnName = TenguUtils.humpToUnderline(field.getName());
            StringBuilder tableColumn = new StringBuilder(columnName); // 字段
            tableColumn.insert(0, "`").append("`");
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
            // 索引
            if (field.isAnnotationPresent(Index.class)) {

            }
            // 主键
            if (field.isAnnotationPresent(PrimaryKey.class)) {
                primaryKey = "\tPRIMARY KEY (`" + columnName + "`),\n";
            }
            // 注释
            if (field.isAnnotationPresent(Comment.class)) {
                Comment comment = field.getDeclaredAnnotation(Comment.class);
                tableColumn.append(" ").append("comment '").append(comment.value()).append("'");
            }
            script.append("\t").append(tableColumn).append(",\n");
        }
        script.append(primaryKey);
        // 结尾
        if (script.charAt(script.length() - 2) == ',') {
            script.deleteCharAt(script.length() - 2);
        }
        script.append(") ENGINE = InnoDB\n");
        script.append("\tDEFAULT CHARACTER SET = utf8\n");
        script.append("\tCOLLATE = utf8_general_ci\n");
        script.append("\tAUTO_INCREMENT = 1;");
        System.out.println(script.toString());
    }

    public static void main(String[] args) throws TenguException {
        ParseModel pm = new ParseModel();
        pm.enhance(UserModel.class);
    }

    /*

        if (type.equals("java.long.Integer")) {

        }
        if (type.equals("java.long.Long")) {

        }
        if (type.equals("java.long.Short")) {

        }
        if (type.equals("java.long.Float")) {

        }
        if (type.equals("java.long.Double")) {

        }
        if (type.equals("java.long.Byte")) {

        }
        if (type.equals("java.long.Boolean")) {

        }
        if (type.equals("java.math.BigDecimal")) {

        }
        if (type.equals("java.math.BigInteger")) {

        }
        if (type.equals("java.math.BigInteger")) {

        }
        if (type.equals("java.util.Date")) {

        }

     */

}
