package org;

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
 * Creates on 2020/3/21 23:23
 */

import org.jetbrains.annotations.NotNull;
import org.poseidon.experiment.UserInfo;
import org.poseidon.experiment.UserModel;
import org.raniaia.poseidon.framework.provide.SQL;

import java.util.List;

/**
 * @author tiansheng
 */
public interface SQLAnnotationTest {

    /*@SQL
        select * from kkb_user_model
        where 1 = 1

        >>
            如果username不等于null就添加
        <<
        :if not username == null
            and username = ${username}
        :else
            and username = "default"

     */
    default List<UserModel> queryUserModel(String username){
        SQLAnnotationKt kt = new SQLAnnotationKt() {
            @NotNull
            @Override
            public List<UserModel> queryUserByUsername() {
                return null;
            }
        };
        return kt.queryUserByUsername();
    }

}
