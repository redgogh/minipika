package com.tengu.db.model;

import com.tengu.annotation.*;
import com.tengu.exception.ParseException;
import com.tengu.exception.TenguException;
import com.tengu.experiment.UserModel;
import com.tengu.tools.StringUtils;
import com.tengu.tools.TenguUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/12 10:19
 * @since 1.8
 */
public class ParseModel {

    /**
     * 对Model进行增强处理
     *
     * @param target
     */
    public void enhance(Class<?> target) throws TenguException {
        String tableName = null; // 表名
        List<String> properties = null; // 字段
        // 判断实体类有没有Model注解
        if (target.isAnnotationPresent(Model.class)) {
            Model model = target.getDeclaredAnnotation(Model.class);
            tableName = model.value();
            if (StringUtils.isEmpty(tableName)) {
                throw new ParseException("Model表名不能为空");
            }
        }
        // 解析字段
        Field[] fields = target.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getDeclaredAnnotation(Column.class);
                String statement = column.value();
                if (StringUtils.isEmpty(statement)) {
                    throw new ParseException("字段属性声明不能为空");
                }
                if(properties == null) properties = new ArrayList<>();
                parseStatement(field, statement, properties);
            }
            if (field.isAnnotationPresent(Index.class)) {

            }
            if (field.isAnnotationPresent(Increase.class)) {

            }
            if (field.isAnnotationPresent(PrimaryKey.class)) {

            }
            if (field.isAnnotationPresent(Comment.class)) {

            }
        }
    }

    /**
     * 解析Column注解中的声明
     *
     * @param field      属性
     * @param statement  声明
     * @param properties 保存着该实体的所有字段
     */
    public void parseStatement(Field field, String statement, List<String> properties) {
        String type = field.getType().getName();
        String underline = TenguUtils.humpToUnderline(field.getName()); // 字段名

        if(type.equals("java.long.String")){

        }
        if(type.equals("java.long.Integer")){

        }
        if(type.equals("java.long.Long")){

        }
        if(type.equals("java.long.Short")){

        }
        if(type.equals("java.long.Float")){

        }
        if(type.equals("java.long.Double")){

        }
        if(type.equals("java.long.Byte")){

        }
        if(type.equals("java.long.Boolean")){

        }
        if(type.equals("java.long.Character")){

        }
        if(type.equals("java.math.BigDecimal")){

        }
    }

    public static void main(String[] args) throws TenguException {
        ParseModel pm = new ParseModel();
        pm.enhance(UserModel.class);
    }

}
