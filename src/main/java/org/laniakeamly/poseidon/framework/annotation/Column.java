package org.laniakeamly.poseidon.framework.annotation;

import java.lang.annotation.*;

/**
 * Model类的字段属性注解
 *
 * Model class the column property annotation.
 *
 * @author TianSheng
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
