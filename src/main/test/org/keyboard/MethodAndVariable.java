package org.keyboard;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright: Create by TianSheng on 2019/12/25 23:57
 */
public class MethodAndVariable {

    private Map<String,String> hashMap = new HashMap<>();
    {
        hashMap.put("username","张三");
    }

    @Test
    public void methodTest(){
        long startTime = System.currentTimeMillis();
        for(int i=0; i<100000000; i++){
            hashMap.get("username").concat("a");
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }

    @Test
    public void variableTest(){
        long startTime = System.currentTimeMillis();
        String value = hashMap.get("username");
        for(int i=0; i<100000000; i++){
            value.concat("a");
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }

}
