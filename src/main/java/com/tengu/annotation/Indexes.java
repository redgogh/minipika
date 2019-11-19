package com.tengu.annotation;

import java.lang.annotation.*;

/**
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/19 16:33
 * @since 1.8
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Indexes {

    /**
     * 单个索引
     * @return
     */
    Index[] value() default {};

}
