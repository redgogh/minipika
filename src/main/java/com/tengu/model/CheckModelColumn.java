package com.tengu.model;

import com.tengu.annotation.Model;
import com.tengu.db.JdbcFunction;
import com.tengu.db.NativeJdbc;
import com.tengu.exception.ParseException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 检测实体是否新增了字段
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/13 13:56
 * @since 1.8
 */
public class CheckModelColumn {

    private static final String ADD_COLUMN_SCRIPT = "ALTER TABLE `%s` ADD %s;";

    public static void check(Class<?> target) throws ParseException {

        if(target.isAnnotationPresent(Model.class)){

            List<String> script = new ArrayList<>();

            Model model = target.getDeclaredAnnotation(Model.class);
            String table = model.value();
            List<String> inDbColumns = JdbcFunction.getTemplate().getColumns(table);
            ModelMessage message = ModelMessage.getMessages().get(table);
            Map<String,String> inMessageColumns = message.getColumns();

            Iterator iter = inMessageColumns.entrySet().iterator();
            while(iter.hasNext()){
                Map.Entry<String,String> entry = (Map.Entry<String, String>) iter.next();
                String key = entry.getKey();
                if(!inDbColumns.contains(key)){
                    String executeScript = String.format(ADD_COLUMN_SCRIPT,message.getTableName(),entry.getValue());
                    NativeJdbc.getJdbc().execute(executeScript);
                }
            }

            System.out.println();

        }else{
            throw new ParseException("没有@Model注解");
        }

    }

}
