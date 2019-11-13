package com.tengu.model;

import com.tengu.annotation.Model;
import com.tengu.db.JdbcTemplate;
import com.tengu.exception.ParseException;

import java.util.List;

/**
 * 检测实体是否新增了字段
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/13 13:56
 * @since 1.8
 */
public class CheckModelColumn {


    public void check(Class<?> target) throws ParseException {

        if(target.isAnnotationPresent(Model.class)){

            Model model = target.getDeclaredAnnotation(Model.class);
            String table = model.value();
            List<String> inDbColumns = JdbcTemplate.getTemplate().getColumns(table);
            List<String> inMessageColumns = ModelMessage.getMessages().get(table).getColumns();

        }else{
            throw new ParseException("没有@Model注解");
        }

    }

}
