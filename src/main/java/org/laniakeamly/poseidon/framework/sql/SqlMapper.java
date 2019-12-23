package org.laniakeamly.poseidon.framework.sql;

import org.laniakeamly.poseidon.framework.container.PrecompileContainer;
import org.laniakeamly.poseidon.framework.sql.xml.build.PrecompiledClass;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by 2BKeyboard on 2019/12/23 11:08
 */
public class SqlMapper {

    private Converter converter = new Converter();
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
        Map<String, Object> parameterMap = new HashMap<>();
        parameter.loader(parameterMap);
        converter.conversion(classValue.getPrecompiledMethod(methodName),parameterMap);
        return this;
    }

}
