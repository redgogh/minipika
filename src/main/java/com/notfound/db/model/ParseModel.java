package com.notfound.db.model;

import com.notfound.annotation.Model;
import com.notfound.tools.StringUtils;

/**
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/12 10:19
 * @since 1.8
 */
public class ParseModel {

    /**
     * 对Model进行增强处理
     * @param target
     */
    public void enhance(Class<?> target){
        String tableName = null;
        // 判断实体类有没有Model注解
        if(target.isAnnotationPresent(Model.class)){
            Model model = target.getDeclaredAnnotation(Model.class);
            String modelValue = model.value();
            if(!StringUtils.isEmpty(modelValue)){

            }else{

            }
        }
    }

}
