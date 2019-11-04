package com.notfound.annotation;

import java.lang.annotation.*;

/**
 * 表的基本实体类.
 * 比如可以在被BaseTable注解的对象中设置表的属性,例如表名的前缀等属性.
 *
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/4 14:31
 * @since 1.8
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BaseTable {
}
