package org.maitreya.poseidon.framework.annotation;

import java.lang.annotation.*;

/**
 * Create by 2BKeyboard on 2019/12/9 17:31
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Valid {

    String name() default "";

}
