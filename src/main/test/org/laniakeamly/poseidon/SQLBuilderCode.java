package org.laniakeamly.poseidon;

import org.junit.Test;

import java.util.HashMap;

/**
 * Create by 2BKeyboard on 2019/12/17 11:37
 */
public class SQLBuilderCode {

    @Test
    public void test1() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("age1",12);
        hashMap.put("age2",15);
        findUserByName(null,hashMap);
    }

    /*
        public String {}(java.lang.StringBuilder sql,java.util.Map<String,Object> map){
            ->
            return sql.toString();
        }
     */
    public void findUserByName(java.lang.StringBuilder sql, java.util.Map<String, Object> map) {
        System.out.println((java.lang.Integer) map.get("age1") >= (java.lang.Integer) map.get("age2"));
        // return sql.toString();
    }

}
