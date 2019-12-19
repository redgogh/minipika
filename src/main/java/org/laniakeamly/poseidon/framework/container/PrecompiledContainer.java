package org.laniakeamly.poseidon.framework.container;

import org.laniakeamly.poseidon.framework.sql.xml.build.PrecompiledClass;
import org.laniakeamly.poseidon.framework.sql.xml.build.PrecompiledMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * 预编译代码容器
 * Create by 2BKeyboard on 2019/12/19 11:24
 */
public class PrecompiledContainer implements CodeContainer{

    private Map<String, PrecompiledClass> map = new HashMap<>();

    @Override
    public PrecompiledClass getClass(String name) {
        return map.get(name);
    }

}
