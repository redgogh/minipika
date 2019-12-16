package org.laniakea.poseidon.framework.annotation;

import java.lang.annotation.*;

/**
 * 字段属性
 * @author 2BKeyboard
 * @version 1.0.0
 * @date 2019/11/12 10:33
 * @since 1.8
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Column {

    String value() default "";

}
