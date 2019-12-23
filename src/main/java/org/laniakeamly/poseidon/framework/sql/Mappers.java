package org.laniakeamly.poseidon.framework.sql;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by 2BKeyboard on 2019/12/23 11:09
 */
public class Mappers {

    private static Map<String,SqlMapper> mapperMap = new HashMap<>();

    public static SqlMapper getMapper(String name){
        if(mapperMap.containsKey(name)){
            return mapperMap.get(name);
        }else{
            mapperMap.put(name,new SqlMapper(name));
            return mapperMap.get(name);
        }
    }

}
