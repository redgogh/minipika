package org.laniakeamly.poseidon.framework.annotation;

import java.lang.annotation.*;

/**
 * config column regular.
 *
 * Copyright: Create by TianSheng on 2020/2/6 13:33
 *
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
@Documented
public @interface Regular {

    /**
     * regular.json file config content.
     * @return regular config content.
     */
    String value();

}
