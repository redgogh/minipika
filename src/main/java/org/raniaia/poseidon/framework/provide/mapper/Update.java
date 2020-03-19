package org.raniaia.poseidon.framework.provide.mapper;

import java.lang.annotation.*;

/**
 *
 * Update annotation, if have more sql will execute batch.
 *
 * <p/>
 * License: <a href="https://github.com/Laniakeamly/poseidon/blob/master/LICENSE">Apache License 2.0</a>
 * <p/>
 * Copyright: Create by TianSheng on 2019/12/17 18:29
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Update {

    /**
     * The sql script.
     */
    String[] value() default {};

}
