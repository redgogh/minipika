package com.test;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by 2BKeyboard on 2019/12/14 21:50
 */
public class LookGeneratorJavaCode {

    public static void main(String[] args) {
        System.out.println(findUserByName());

    }

    public static Object findUserByName(){
        Map<String,Object> map = new HashMap<>();
        map.put("age1",20);
        map.put("age2",30);
        return map.get("age1").equals(map.get("age2"));
    }

}