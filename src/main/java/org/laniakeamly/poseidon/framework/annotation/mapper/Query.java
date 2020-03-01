package org.laniakeamly.poseidon.framework.annotation.mapper;

import org.laniakeamly.poseidon.framework.db.NativeJdbc;

import java.lang.annotation.*;
import java.util.List;

/**
 *
 * {@code Query}注解只对于Mapper接口中的方法有效，如果Mapper接口中的方法
 * 被注解{@code Query}, 那么这个方法就是执行的插入，而不是查询或者其他。
 *
 * Representative mapper interface method use query sql.
 *
 * <p>This annotation only for mapper interface methods effective.</p>
 *
 * When you use be annotation method time, poseidon will calling
 * {@link NativeJdbc#executeQuery(String, Object...)}.
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
     * 默认查询{@code Object},
     * 代表返回对象为{@code Object}而不是{@code List}或者其他
     *
     * if you mode paramter value is {@link QueryMode#DEFAULT}, then query
     * result is you mapper method statement the result.
     *
     * if you mode parameter value is {@link QueryMode#OBJECT}, then query
     * result is {@link Object}.
     *
     * if you mode parameter value is {@link QueryMode#LIST}, then query
     * result is {@link List}
     *
     * of course you can choose not to set mode parameter, query result is you
     * method statement the result.
     *
     */
    QueryMode mode() default QueryMode.DEFAULT;

}
