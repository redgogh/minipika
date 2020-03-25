package org.keyboard;

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

/*
 * Creates on 2020/2/8 21:22
 */

import org.junit.Test;

import org.raniaia.poseidon.BeansManager;
import org.raniaia.poseidon.components.jdbc.JdbcSupport;
import org.poseidon.experiment.UserInfo;

/**
 * @author tiansheng
 * @since 1.8
 */
public class SaveUserModel {

    private JdbcSupport jdbcSupport = BeansManager.get("jdbc");

    @Test
    public void test(){
        UserInfo info = new UserInfo();
        info.setName("test01");
        info.setEmail("raniaia@123.com");
        System.out.println(jdbcSupport.insert(info));
    }

}
