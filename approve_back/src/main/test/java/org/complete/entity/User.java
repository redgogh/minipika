package org.complete.entity;

/*
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
 */



/*
 * Creates on 2020/3/23 23:14
 */

import lombok.Getter;
import lombok.Setter;
import org.jiakesiws.minipika.framework.provide.entity.Column;
import org.jiakesiws.minipika.framework.provide.entity.Comment;
import org.jiakesiws.minipika.framework.provide.entity.Entity;
import org.jiakesiws.minipika.framework.provide.entity.PK;

/**
 * @author tiansheng
 */
@Entity("user")
@Getter
@Setter
public class User {

    @PK
    @Column("int(11) not null")
    private Integer id;

    @Column("varchar(255) not null")
    @Comment("用户名")
    private String username;

    @Column("varchar(255) not null")
    @Comment("用户密码")
    private String password;

    @Column("varchar(255) not null")
    @Comment("电子邮箱")
    private String email;

    public User() {
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
