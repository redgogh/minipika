package com.tractor.annotation;

import java.lang.annotation.*;

/**
 * 忽略某个字段，被忽略字段不会对数据库进行影响
 * @author 2Bkeyboard
 * @version 1.0.0
 * @date 2019/11/13 15:49
 * @since 1.8
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Ignore {
}
