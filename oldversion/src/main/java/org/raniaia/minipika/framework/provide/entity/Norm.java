package org.jiakesimk.minipika.framework.provide.entity;

/* ************************************************************************
 *
 * Copyright (C) 2020 tiansheng All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ************************************************************************/

/*
 * Creates on 2020/2/6.
 */

import java.lang.annotation.*;

/**
 * 配置Entity类字段约束
 * 被约束的字段只能按照要求才可以执行insert或者update等方法
 *
 * 比如说我给email字段加上一个验证邮箱的正则表达式，如果通过了验证那么这个Entity类就可以被保存
 * 反之如果传入的字段不是邮箱格式，那么这个Entity类就不能被保存。
 * 执行insert或update方法时返回结果就是0,但是不会提示返回0是什么原因
 *
 * 如果是根据sql执行的，那么配置的这个就不会生效。只有{@link JdbcSupport#update(Object)}
 * 或者{@link JdbcSupport#insert(Object)}等类似方法才会生效。
 *
 *
 * config column norm.
 * Constraints field only on request can be save.
 *
 * Such as i give email field add a email verify the regex
 * if minipikad then this entity can save otherwise cant be save.
 * verification failed entity execute insert or update return result 0.
 *
 * but not will prompt return 0 what is the reason.
 *
 * Such as execute by sql then config will not take effect
 * just execute{@link JdbcSupport#insert(Object)} or {@link JdbcSupport#update(Object)} etc similar method.
 *
 * @author tiansheng
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
@Documented
public @interface Norm {

    /**
     * norm.json file config content.
     */
    String value();

}
