package org.raniaia.poseidon.framework.provide.model;

import java.lang.annotation.*;
import org.raniaia.poseidon.framework.db.JdbcSupport;

/**
 * 配置Model类字段约束
 * 被约束的字段只能按照要求才可以执行insert或者update等方法
 *
 * 比如说我给email字段加上一个验证邮箱的正则表达式，如果通过了验证那么这个Model类就可以被保存
 * 反之如果传入的字段不是邮箱格式，那么这个Model类就不能被保存。
 * 执行insert或update方法时返回结果就是0,但是不会提示返回0是什么原因
 *
 * 如果是根据sql执行的，那么配置的这个就不会生效。只有{@link JdbcSupport#update(Object)}
 * 或者{@link JdbcSupport#insert(Object)}等类似方法才会生效。
 *
 *
 * config column regular.
 * Constraints field only on request can be save.
 *
 * Such as i give email field add a email verify the regex
 * if approved then this model can save otherwise cant be save.
 * verification failed model execute insert or update return result 0.
 *
 * but not will prompt return 0 what is the reason.
 *
 * Such as execute by sql then config will not take effect
 * just execute{@link JdbcSupport#insert(Object)} or {@link JdbcSupport#update(Object)} etc similar method.
 *
 * Copyright: Create by tiansheng on 2020/2/6 13:33
 *
 * @author tiansheng
 * @version 1.0.0
 * @since 1.8
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
@Documented
public @interface Regular {

    /**
     * regular.json配置文件中的key
     *
     * regular.json file config content.
     * @return regular config content.
     */
    String value();

}
