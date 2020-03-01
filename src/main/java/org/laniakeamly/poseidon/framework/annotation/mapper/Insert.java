package org.laniakeamly.poseidon.framework.annotation.mapper;

import org.laniakeamly.poseidon.framework.db.NativeJdbc;

import java.lang.annotation.*;

/**
 *
 * {@code Insert}注解只对Mapper接口方法有效，如果方法被注解了{@code Insert}，那么当你调用的时候
 * 就是执行插入。
 *
 * 当你调用被{@code Execute}注解的方法时，框架底层会去调用{@link
 * NativeJdbc#executeUpdate(String, Object...)}方法。
 *
 * Representative mapper interface method use insert.
 *
 * <p>This annotation only for mapper interface methods effective.</p>
 *
 * When you use be annotation the method time, poseidon will calling {@link
 * NativeJdbc#executeUpdate(String, Object...)}.
 *
 * Copyright by TianSheng on 2020/2/29 1:53
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Insert {
}
