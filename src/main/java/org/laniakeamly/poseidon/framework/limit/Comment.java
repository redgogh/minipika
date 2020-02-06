package org.laniakeamly.poseidon.framework.limit;

import java.lang.annotation.*;

/**
 * 字段注释
 *
 * column comment.
 *
 * Create by 2BKeyboard on 2019/11/12 10:35
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
