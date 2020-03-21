package org.poseidon;

import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Copyright: Create by tiansheng on 2019/12/17 11:32
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