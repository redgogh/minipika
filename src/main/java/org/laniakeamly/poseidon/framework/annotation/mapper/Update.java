package org.laniakeamly.poseidon.framework.annotation.mapper;

import org.laniakeamly.poseidon.framework.db.NativeJdbc;

import java.lang.annotation.*;

/**
 *
 * 被{@code Update}注解的方法在你调用的时候会执行更新操作。
 *
 * Representative mapper interface method use update.
 *
 * <p>This annotation only for mapper interface methods effective.</p>
 *
 * When you use be annotation the method time, poseidon will calling
 * {@link NativeJdbc#executeUpdate(String, Object...)}.
 *
 * Copyright by TianSheng on 2020/2/29 1:53
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Update {
}
