package com.tengu.annotation;

import java.lang.annotation.*;

/**
 * 添加索引
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
    String alias();

    /**
     * 索引字段
     */
    String[] columns() default {};

    /**
     * 索引类型
     * @return
     */
    IndexType type() default IndexType.NORMAL;

    /**
     * 注释
     * @return
     */
    String comment() default "";

}
