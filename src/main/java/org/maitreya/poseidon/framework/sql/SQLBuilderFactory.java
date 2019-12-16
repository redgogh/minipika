package org.maitreya.poseidon.framework.sql;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by 2BKeyboard on 2019/12/11 12:06
 */
public class SQLBuilderFactory {

    private static SQLBuilderFactory factory;
    private final Map<String, SQLBuilder> values;

    public SQLBuilderFactory() {
        values = new HashMap<>(); // initial
    }

    public static SQLBuilderFactory getFactory() {
        if (factory == null) {
            factory = new SQLBuilderFactory();
        }
        return factory;
    }

    public SQLBuilder getBuilder(String name) {
        return values.get(name);
    }

}
