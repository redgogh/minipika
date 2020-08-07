package org.jiakesiws.minipika;

/* ************************************************************************
 *
 * Copyright (C) 2020 2B键盘 All rights reserved.
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



import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Copyright: Create by 2B键盘 on 2019/12/17 11:32
 */
public class Example {

    @Test
    public void objToArray() {

        Map map = new HashMap<>();

        List<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");

        map.put("list", list);

        for (String s : (List<java.lang.String>) map.get("list")) {
            System.out.println(s);
        }

    }

    @Test
    public void getListGeneric() {
        List<String> list = new LinkedList<>();
        System.out.println(list.isEmpty());
    }

    public java.lang.String findUserByName(java.util.Map map, java.util.List params) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from user where 1 = 1");
        if ((java.lang.String) map.get("username") != null) {
            sql.append("usernameand user_a = ?");
            params.add(map.get("username"));
        } else {
            sql.append("and user_b = ?");
            params.add(map.get("username"));
        }
        sql.append("limit 1,0");
        return sql.toString();
    }


}