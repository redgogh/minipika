package org.laniakeamly.poseidon.framework.tools;

import java.util.Map;

/**
 * Create by 2BKeyboard on 2020/1/19 18:30
 */
public class Maps {

    /**
     * 获取第一个Key
     * @param map
     * @return
     */
    public static Object getFirstKey(Map map){
        for (Object o : map.keySet()) {
            return o;
        }
        return null;
    }

    /**
     * 获取第一个值
     * @param map
     * @return
     */
    public static Object getFirstValue(Map map){
        for (Object value : map.values()) {
            return value;
        }
        return null;
    }

    /**
     * 根据下标获取Key
     * @param map
     * @param index
     * @return
     */
    public static Object getKey(Map map,int index){
        int count = 0;
        for (Object value : map.keySet()) {
            if(count == index) return value;
            count++;
        }
        return null;
    }

    /**
     * 根据下标获取值
     * @param map
     * @param index
     * @return
     */
    public static Object getValue(Map map,int index){
        int count = 0;
        for (Object value : map.values()) {
            if(count == index) return value;
            count++;
        }
        return null;
    }

}
