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



import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Copyright: Create by 2B键盘 on 2019/12/16 23:57
 */
@SuppressWarnings("unchecked")
public class CompilerSQLMethod {

    public static void main(String... args){
        Map<String,Object> map = new HashMap<>();
        LinkedList params = new LinkedList();
        map.put("age",18);

        //
        // sql
        //
        StringBuilder sql = new StringBuilder("select * from user where 1=1 ");

        Object age = map.get("age");

        if((Integer) age < 18){
            sql.append("and age = ?");
            params.add(map.get(age));
        }

    }



}
