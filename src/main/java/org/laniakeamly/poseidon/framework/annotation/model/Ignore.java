package org.laniakeamly.poseidon.framework.annotation.model;

import java.lang.annotation.*;

/**
 * 忽略某个字段，被忽略字段不会对数据库进行影响
 *
 * This annotation only to have {@link Model} the class effective.
 *
 * Ignore column, be ignored column will not impact on the database.
 *
 * @author TianSheng
 * @version 1.0.0
 * @date 2019/11/13 15:49
 * @since 1.8
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Ignore {
}
