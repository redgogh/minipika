package org.laniakeamly.poseidon.framework.limit;

import java.lang.annotation.*;

/**
 * 配置字段规则
 *
 * config column regular.
 *
 * Create by 2BKeyboard on 2020/2/6 13:33
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Regular {

    /**
     * 正则表达式
     * @return
     */
    String value();

}
