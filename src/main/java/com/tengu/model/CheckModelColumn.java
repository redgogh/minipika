package com.tengu.model;

import com.tengu.annotation.Model;
import com.tengu.db.JdbcTemplate;
import com.tengu.exception.ParseException;

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


    public static void check(Class<?> target) throws ParseException {

        if(target.isAnnotationPresent(Model.class)){

            Model model = target.getDeclaredAnnotation(Model.class);
            String table = model.value();
            List<String> inDbColumns = JdbcTemplate.getTemplate().getColumns(table);
            Map<String,String> inMessageColumns = ModelMessage.getMessages().get(table).getColumns();

            int temp = 0;
            Iterator iter = inMessageColumns.entrySet().iterator();
            while(iter.hasNext()){
                Map.Entry<String,String> entry = (Map.Entry<String, String>) iter.next();
                String inMsg = entry.getKey();
                String indb = inDbColumns.get(temp);
                // 如果不等于就对当前字段进行创建
                if(!inMsg.equals(indb)){

                }
                temp++;
            }

            System.out.println();

        }else{
            throw new ParseException("没有@Model注解");
        }

    }

}
