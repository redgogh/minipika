package org.laniakeamly.poseidon.framework.annotation.mapper;

import java.lang.annotation.*;

/**
 * Copyright by TianSheng on 2020/2/29 1:49
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Execute {

    ExeMode mode() default ExeMode.DEFAULT;

}
