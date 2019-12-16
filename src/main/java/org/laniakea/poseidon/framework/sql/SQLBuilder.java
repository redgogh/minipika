package org.laniakea.poseidon.framework.sql;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Create by 2BKeyboard on 2019/12/10 17:34
 */
public class SQLBuilder implements Serializable {

    private String name;

    public SQLBuilder(String name, SQLConstruct construct){
        this.name = name;
        SQLBuilderFactory factory = SQLBuilderFactory.getFactory();
        Map params = new HashMap();
        construct.param(params);
    }

}
