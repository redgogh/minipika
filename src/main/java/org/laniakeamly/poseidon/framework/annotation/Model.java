package org.laniakeamly.poseidon.framework.annotation;

import java.lang.annotation.*;

/**
 * Table注解,被注解的对象代表是数据库的一张表.
 * 注解中可以设置表的各个属性,从而无需SQL直接创建表.
 *
 * Table annotation can set table properties.
 * No need use sql create table.
 *
 * @author TianSheng
 * @version 1.0.0
 * @date 2019/11/4 14:22
 * @since 1.8
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Model {

    /**
     * 表名
     * @return
     */
    String value() default "";

    /**
     * 储存引擎
     * @return
     */
    Engine engine() default Engine.INNODB;

    /**
     * 自增长从多少开始
     * @return
     */
    int increment() default 1;

}
