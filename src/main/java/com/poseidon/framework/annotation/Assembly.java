package com.poseidon.framework.annotation;

import java.lang.annotation.*;

/**
 * 实例化注解
 * Create by 2BKeyboard on 2019/12/5 17:18
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Assembly {

    String value() default "";

}
