package org.laniakeamly.poseidon.framework.container;

import org.laniakeamly.poseidon.framework.sql.xml.build.PrecompiledClass;

/**
 * Create by 2BKeyboard on 2019/12/19 11:30
 */
public interface CodeContainer {

    /**
     * 获取一个预编译的类
     * @param name
     * @return
     */
    PrecompiledClass getClass(String name);

}
