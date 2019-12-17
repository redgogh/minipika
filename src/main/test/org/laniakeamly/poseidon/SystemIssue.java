package org.laniakeamly.poseidon;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by 2BKeyboard on 2019/12/17 11:32
 */
public class SystemIssue {

    @Test
    public void objToArray() {

        Map map = new HashMap<>();
        // Object objects = new Object[]{1, 2.1, "name"};
        Object objects = new String[]{"aaa", "bbb", "ccc"};

        for (Object object : (Object[]) objects) {
            System.out.println(object);
        }

    }

}