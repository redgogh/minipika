package org.laniakeamly.poseidon.framework.limit;

import java.lang.annotation.*;

/**
 * bean容器注入注解
 *
 * bean container inject annotation.
 *
 * Create by 2BKeyboard on 2019/12/9 17:31
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Valid {

    String name() default "";

}
