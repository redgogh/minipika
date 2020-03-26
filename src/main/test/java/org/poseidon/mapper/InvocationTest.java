package org.poseidon.mapper;

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



import org.junit.Test;
import org.raniaia.approve.BeansManager;
import org.raniaia.approve.framework.provide.mapper.Query;

import org.poseidon.experiment.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright by tiansheng on 2020/2/29 1:43
 * @author tiansheng
 * @version 1.0.0
 * @since 1.8
 */
public class InvocationTest {

    @Test
    public void mapperInvocationTest0(){
        UserMapper mapper = BeansManager.newInstance(UserMapper.class);
        // UserMapper mapper = (UserMapper) MapperInvocation.invoker(UserMapper.class);
        // System.out.println(mapper.findUsernameById(4001));
        // System.out.println(mapper.updateUsernameById(4001,"testX"));
        List<UserInfo> list = new ArrayList<>();
        list.add(new UserInfo("l-test-01","xxxx"));
        list.add(new UserInfo("l-test-02","xxxx"));
        list.add(new UserInfo("l-test-03","xxxx"));
        // mapper.addUsers(list);
        mapper.updateUsernameById(4032,"test00001");
    }

    interface UserMapper{

        String findUsernameById(Integer id);

        void updateUsernameById(Integer id,String name);

        void addUsers(List<UserInfo> users);

        @Query("select * from kkb_user_info where id = ?")
        UserInfo findUserInfoById(Integer id);

    }

    @Test
    public void test02(){
    }

}
