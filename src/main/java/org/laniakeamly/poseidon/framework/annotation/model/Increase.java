package org.laniakeamly.poseidon.framework.annotation.model;

import java.lang.annotation.*;

/**
 * 自增长
 *
 * Auto increase
 *
 * @author TianSheng
 * @version 1.0.0
 * @date 2019/11/12 10:18
 * @since 1.8
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Increase {
}
