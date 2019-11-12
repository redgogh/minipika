package com.tengu.annotation;

import java.lang.annotation.*;

/**
 * 主键注解,此注解需要放在Model类上
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/4 15:06
 * @since 1.8
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PrimaryKey {

    /**
     * 是否自增长
     * @return
     */
    boolean increase() default true;

}
