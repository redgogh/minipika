package org.approve.experiment;

/*
 * Copyright (C) 2020 Tiansheng All rights reserved.
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
 */



import lombok.Data;
import org.raniaia.approve.framework.provide.model.*;

import java.util.Date;

/**
 * {@link Model}中有三个属性
 *
 *      {@link Model#value()}       是表名
 *      {@link Model#engine()}      是数据库引擎，数据库引擎可以使用{@link Engine}枚举类
 *      {@link Model#increment()}   设置自增长从多少开始
 *
 *
 * {@link Model} have three a property
 *      {@link Model#value()}       table name
 *      {@link Model#engine()}      database engine , database engine can use{@link Engine} enum class.
 *      {@link Model#increment()}   increment from how much start.
 *
 * <h5>Copyright by tiansheng on 2020/2/9 0:16</h5>
 * License:
 * <a herf="https://github.com/Laniakeamly/approve/blob/master/LICENSE">Apache License 2.0</a>
 *
 * @author tiansheng
 * @version 1.0.0
 * @since 1.8
 */
@Data
@Model("example_model")
public class ExampleModel {

    /**
     * {@link PK} 表示主键
     *
     * {@link PK} Represents the primary key
     */
    @PK
    @Column("int(11) not null")
    @Comment("id")
    private int id;

    @Column("datetime not null")
    @Comment("用户名")
    private Date createTime;

    @Column("varchar(50) not null")
    @Comment("用户名")
    private String username;

    /**
     * {@link Norm}是配置字段的约束注释
     * email这个值是在resource目录下的norm.json中配置的
     */
    @Norm("email")
    @Column("varchar(2000) not null")
    @Comment("用户邮箱")
    private String email;

    /**
     * ignore表示忽略此字段
     * 被ignore注解的字段不会被创建的数据库，也不会和数据库打任何交道
     */
    @Ignore
    private String what;

}
