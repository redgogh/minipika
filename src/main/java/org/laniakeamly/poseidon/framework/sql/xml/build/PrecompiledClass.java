package org.laniakeamly.poseidon.framework.sql.xml.build;

import lombok.Getter;
import lombok.Setter;
import org.laniakeamly.poseidon.framework.sql.xml.ProvideConstant;
import org.laniakeamly.poseidon.framework.tools.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright: Create by TianSheng on 2019/12/18 19:26
 */
public class PrecompiledClass {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String fullName;

    @Getter
    @Setter
    private boolean load = false;

    @Getter
    @Setter
    private Class<?> iClass;

    Map<String, PrecompiledMethod> methods = new HashMap(6);

    public PrecompiledClass() {
    }

    public PrecompiledClass(String name) {
        this.name = name;
        this.fullName = ProvideConstant.CLASS_FULL_NAME.concat(name);
    }

    public PrecompiledMethod getPrecompiledMethod(String name) {
        PrecompiledMethod pm = methods.get(name);
        if (pm == null) {
            throw new NullPointerException(StringUtils.format("'{}' mapper is not found please check your mapper xml in mapper '{}'", name, this.name));
        }
        return pm;
    }

    public void addPrecompiledMethod(PrecompiledMethod method) {
        methods.put(method.getName(), method);
    }

}
