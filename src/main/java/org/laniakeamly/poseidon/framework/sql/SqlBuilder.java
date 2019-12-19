package org.laniakeamly.poseidon.framework.sql;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by 2BKeyboard on 2019/12/19 11:53
 */
public class SqlBuilder {

    private String name;

    public SqlBuilder(String name){
        this.name = name;
    }

    public SqlMapper buildMapper(String mapper,SqlParameter sqlParameter){
        Map<String,Object> parameters = new HashMap<>();
        sqlParameter.set(parameters);
        return SqlMapper.builderMapper(name,mapper,parameters);
    }

}
