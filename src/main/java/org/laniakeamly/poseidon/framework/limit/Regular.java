package org.laniakeamly.poseidon.framework.limit;

import java.lang.annotation.*;

/**
 *
 * config column regular.
 *
 * Copyright: Create by 2BKeyboard on 2020/2/6 13:33
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Regular {

    /**
     * regular.json file config content.
     * @return regular config content.
     */
    String value();

}
