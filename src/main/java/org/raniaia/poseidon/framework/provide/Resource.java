package org.raniaia.poseidon.framework.provide;

import java.lang.annotation.*;

/**
 *
 * 标记Bean容器中的对象名称
 * 如果name为空，则默认为对象名称
 *
 * Copyright: Create by tiansheng on 2019/12/10 10:43
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Resource {

    String name() default "";

}
