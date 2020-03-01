package org.laniakeamly.poseidon.framework.annotation.mapper;

import java.lang.annotation.*;

import org.laniakeamly.poseidon.framework.db.NativeJdbc;

/**
 *
 * Execute注解只对Mapper接口方法有效，如果方法被注解了Execute，那么当你调用的时候
 * 就是执行execute而不是查询。
 *
 * 当你调用被{@code Execute}注解的方法时，框架底层会去调用{@link
 * NativeJdbc#execute(String, Object...)}方法。
 *
 * 如果你的mode参数设置是执行批量{@link ExeMode#BATCH}，那么底层就会调用
 * {@link NativeJdbc#executeBatch(String, Object...)}.
 *
 * Representative mapper interface method use execute sql not query.
 *
 * <p>This annotation only for mapper interface methods effective.</p>
 *
 * When you use be annotation the method time, poseidon will calling
 * {@link NativeJdbc#execute(String, Object...)}.
 *
 * if mode parameter is {@link ExeMode#BATCH} time, poseidon will calling
 * {@link NativeJdbc#executeBatch(String, Object...)}.
 *
 * Copyright by TianSheng on 2020/2/29 1:49
 *
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Execute {

    /**
     * Set sql execute mode.
     * Parameter mode default value is {@link ExeMode#DEFAULT}.
     */
    ExeMode mode() default ExeMode.DEFAULT;

}
