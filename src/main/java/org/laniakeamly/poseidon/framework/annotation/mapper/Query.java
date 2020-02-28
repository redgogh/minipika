package org.laniakeamly.poseidon.framework.annotation.mapper;

import java.lang.annotation.*;

/**
 *
 * 此注解只生效在Mapper接口中的方法上
 * 被注解的方法代表是一个查询方法
 *
 * Copyright by TianSheng on 2020/2/29 1:46
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Query {

    /**
     * 默认查询Object
     * @return
     */
    QueryMode mode() default QueryMode.OBJECT;

}
