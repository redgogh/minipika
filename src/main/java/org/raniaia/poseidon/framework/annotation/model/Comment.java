package org.raniaia.poseidon.framework.annotation.model;

import java.lang.annotation.*;

/**
 * This annotation only to have {@link Model} the class effective.
 *
 * {@code Comment} annotation is column comment, you can write column
 * comment in {@link Comment#value()}.
 *
 * Copyright: Create by TianSheng on 2019/11/12 10:35
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Comment {

    /**
     * 字段注释
     *
     * @return
     */
    String value() default "";

    /**
     * 索引注释
     *
     * @return
     */
    String index() default "";

}
