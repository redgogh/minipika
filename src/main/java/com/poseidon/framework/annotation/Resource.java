package com.poseidon.framework.annotation;

import java.lang.annotation.*;

/**
 * Create by 2BKeyboard on 2019/12/10 10:43
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Resource {

    String name() default "";

}
