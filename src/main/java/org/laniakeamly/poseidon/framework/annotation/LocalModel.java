package org.laniakeamly.poseidon.framework.annotation;

import org.laniakeamly.poseidon.framework.local.database.LocalDatabase;

import java.lang.annotation.*;

/**
 *
 * {@code LocalModel} is {@link LocalDatabase} the annotation.
 *
 * <p/>
 * License: <a href="https://github.com/Laniakeamly/poseidon/blob/master/LICENSE">Apache License 2.0</a>
 * <p/>
 * Copyright: Create by TianSheng on 2019/12/17 18:29
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LocalModel {

    /**
     * table name
     */
    String value();

}
