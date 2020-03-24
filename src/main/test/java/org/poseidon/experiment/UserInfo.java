package org.poseidon.experiment;

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
import org.raniaia.poseidon.framework.provide.model.Column;
import org.raniaia.poseidon.framework.provide.model.Model;
import org.raniaia.poseidon.framework.provide.model.PK;
import org.raniaia.poseidon.framework.provide.model.Norm;

/**
 * Copyright: Create by tiansheng on 2020/1/17 14:01
 */
@Data
@Model(value = "user_info",increment = 4000)
public class UserInfo {

    @PK
    @Column("int(11) not null")
    private int id;

    @Column("varchar(255) not null")
    private String name;

    @Norm("email")
    @Column("varchar(255) not null")
    private String email;

    public UserInfo() {
    }

    public UserInfo(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
