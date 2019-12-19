package org.laniakeamly.poseidon.framework.container;

import org.laniakeamly.poseidon.framework.sql.xml.build.PrecompiledClass;

/**
 * Create by 2BKeyboard on 2019/12/19 15:01
 */
public class SqlClass implements Container {

    @Override
    public <T> T getValue(String name) {
        return null;
    }

    /**
     * 加载一个类对象,只加载类信息不加载任何方法
     * @param target
     */
    public void loaderClass(PrecompiledClass target){
        // 表示自己已经被加载过了
        target.setLoad(true);
    }

}
