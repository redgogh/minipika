package com.notfound.annotation;

import java.lang.annotation.*;

/**
 * 数据库实体类,被Database注解的对象将会在启动的时候创建该数据库.
 * Database注解的实体需要被Model类继承,这样框架才知道Model实体属于哪个数据库,从而简化开发.
 *
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/4 15:05
 * @since 1.8
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Database {
}
