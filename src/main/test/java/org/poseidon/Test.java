package org.poseidon;

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



import org.poseidon.experiment.UserModel;

import org.raniaia.poseidon.BeansManager;
import org.raniaia.poseidon.components.db.JdbcSupport;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Copyright: Create by tiansheng on 2019/12/20 10:46
 */
public class Test {

    private JdbcSupport jdbcSupport = BeansManager.get("jdbc");

    @org.junit.Test
    public void insertUserModel() {
        Map map = new HashMap();
        UserModel userModel1 = new UserModel();
        userModel1.setUserName("aaaaaaaaaaaaa1");
        userModel1.setGoogleEmail("aaaaaaaaaaaaa2");
        userModel1.setAddress("aaaaaaaaaaaaa3");
        userModel1.setUuid("aaaaaaaaaaaaa4");
        userModel1.setCreateTime(new Date());
        List<UserModel> list = new ArrayList<>();
        list.add(userModel1);
        map.put("users", list);
        java.util.List<UserModel> $user = (List<UserModel>) map.get("users");
        System.out.println();
    }

    @org.junit.Test
    public void test2() {
        String abc = " INSERT INTO `user_model`( `user_name`, `google_email`, `password`, `user_age`, `address`, `uuid`, `product_name`, `create_time`) VALUES ( ?, 'c28a7745-7508-48a0-820e-a5cd14748d24', '123456', 18, 'c28a7745-7508-48a0-820e-a5cd14748d24', 'c28a7745-7508-48a0-820e-a5cd14748d24', '敌敌畏', '2019-12-03 09:39:19' );  INSERT INTO `user_model`( `user_name`, `google_email`, `password`, `user_age`, `address`, `uuid`, `product_name`, `create_time`) VALUES ( ?, 'c28a7745-7508-48a0-820e-a5cd14748d24', '123456', 18, 'c28a7745-7508-48a0-820e-a5cd14748d24', 'c28a7745-7508-48a0-820e-a5cd14748d24', '敌畏;', '2019-12-03 09:39:19' );  ";
        jdbcSupport.update(abc, "abc", "cba");
    }

    public static void main(String[] args) {
        int[] arrays = new int[]{1,2,3};
        Object obj = arrays;
        System.out.println(obj instanceof Array);
    }

}
