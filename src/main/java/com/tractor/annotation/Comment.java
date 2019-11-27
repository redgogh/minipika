package com.tractor.annotation;

import java.lang.annotation.*;

/**
 * 注释
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/12 10:35
 * @since 1.8
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Comment {

    /**
     * 字段注释
     * @return
     */
    String value() default "";

    /**
     * 索引注释
     * @return
     */
    String index() default "";

}
