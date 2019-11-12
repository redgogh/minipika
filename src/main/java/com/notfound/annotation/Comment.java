package com.notfound.annotation;

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

    String value() default "";

}
