package org.laniakeamly.poseidon.framework.sql.xml.build;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by 2BKeyboard on 2019/12/18 19:26
 */
public class PrecompiledClass {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String fullName;

    Map<String,PrecompiledMethod> methods = new HashMap(6);

    public PrecompiledClass(){}

    public PrecompiledClass(String name) {
        this.name = name;
        this.fullName = "org.laniakeamly.poseidon.$builder.".concat(name);
    }

    public PrecompiledMethod getPrecompiledMethod(String name) {
        return methods.get(name);
    }

    public void addPrecompiledMethod(PrecompiledMethod method) {
        methods.put(method.getName(),method);
    }

}
