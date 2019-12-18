package org.laniakeamly.poseidon.framework.sql.xml.build;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by 2BKeyboard on 2019/12/18 19:26
 */
public class DynamicClass {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String fullName;

    List<DynamicMethod> methods = new ArrayList<>(6);

    public DynamicClass(){}

    public DynamicClass(String name) {
        this.name = name;
        this.fullName = "org.laniakeamly.poseidon.$builder.".concat(name);
    }

    public void addDynamicMethod(DynamicMethod method) {
        methods.add(method);
    }

}
