package org.laniakeamly.poseidon.framework.sql;

import org.laniakeamly.poseidon.framework.container.PrecompileContainer;
import org.laniakeamly.poseidon.framework.sql.xml.build.PrecompiledClass;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by 2BKeyboard on 2019/12/23 11:08
 */
public class SqlMapper {

    private PrecompileContainer container = PrecompileContainer.getContainer();

    /**
     * class
     */
    private PrecompiledClass classValue;

    public SqlMapper() {
    }

    public SqlMapper(String name) {
        this.classValue = container.getValue(name);
    }

    public SqlMapper build(String methodName, Parameter parameter) {
        Map<String, Object> param = new HashMap<>();
        parameter.loader(param);
        Converter converter = new Converter();
        converter.build();
        return this;
    }

}
