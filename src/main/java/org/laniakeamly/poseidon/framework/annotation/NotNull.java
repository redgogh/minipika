package org.laniakeamly.poseidon.framework.annotation;

import java.lang.annotation.*;

/**
 * 表示字段不能为空
 *
 * Column cannot null.
 *
 * Create by 2BKeyboard on 2019/12/11 10:53
 */
@Target(ElementType.LOCAL_VARIABLE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NotNull {
}
