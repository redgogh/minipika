package com.notfound.annotation;

import java.lang.annotation.*;

/**
 * 索引,被注解的成员将会成为该表的索引.
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/4 15:11
 * @since 1.8
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Index {

    /**
     * 索引别名
     * @return
     */
    String alias() default "";

}
