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
 * Creates on 2019/11/4.
 */

import java.lang.annotation.*;

/**
 * Table注解,被注解的对象代表是数据库的一张表.
 * 注解中可以设置表的各个属性,从而无需SQL直接创建表.
 *
 * Table annotation can set table properties.
 * No need use sql create table.
 *
 * @author tiansheng
 * @email jiakesiws@gmail.com
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Entity {

    /**
     * 表名
     * @return
     */
    String value() default "";

    /**
     * 储存引擎
     * @return
     */
    Engine engine() default org.jiakesimk.minipika.framework.provide.entity.Engine.INNODB;

    /**
     * 自增长从多少开始
     * @return
     */
    int increment() default 1;

}
