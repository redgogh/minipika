package com.tractor.model;

import com.tractor.annotation.Model;

/**
 * 检查一些类是否遵守标准
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/27 15:56
 * @since 1.8
 */
public class SecurityManager {

    public static boolean existModel(Class<?> target){
        return target.isAnnotationPresent(Model.class);
    }

}
