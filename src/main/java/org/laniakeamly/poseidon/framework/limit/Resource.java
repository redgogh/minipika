package org.laniakeamly.poseidon.framework.limit;

import java.lang.annotation.*;

/**
 * Copyright: Create by 2BKeyboard on 2019/12/10 10:43
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Resource {

    String name() default "";

}
