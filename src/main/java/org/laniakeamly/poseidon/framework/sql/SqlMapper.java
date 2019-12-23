package org.laniakeamly.poseidon.framework.sql;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by 2BKeyboard on 2019/12/23 11:08
 */
public class SqlMapper {

    /**
     * mapper name
     */
    private String name;

    public SqlMapper() {
    }

    public SqlMapper(String naem) {
        this.name = name;
    }

    public SqlMapper build(String name, Parameter parameter) {
        Map<String, Object> param = new HashMap<>();
        parameter.loader(param);
        return this;
    }

}
