package org.poseidon;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Copyright: Create by TianSheng on 2019/12/16 23:57
 */
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
