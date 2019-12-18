package org.laniakeamly.poseidon;

import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Create by 2BKeyboard on 2019/12/17 11:32
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

        map.put("list",list);

        for(String s : (List<java.lang.String>) map.get("list")){
            System.out.println(s);
        }

    }

    @Test
    public void getListGeneric(){
        List<String> list = new LinkedList<>();
        System.out.println(list.isEmpty());
    }

}