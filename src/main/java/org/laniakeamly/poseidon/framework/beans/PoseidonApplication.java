package org.laniakeamly.poseidon.framework.beans;

/**
 *
 * Public beans manager object.
 *
 * <p/>
 * License: <a href="https://github.com/Laniakeamly/poseidon/blob/master/LICENSE">Apache License 2.0</a>
 * <p/>
 * Copyright: Create by TianSheng on 2019/12/17 18:29
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 */
public class PoseidonApplication {

    /**
     * get bean
     */
    public static <T> T getBean(String name){
        return BeansManager.getBean(name);
    }

    /**
     * get mapper
     */
    public static <T> T getMapper(Class<T> mapperClass){
        return BeansManager.getMapper(mapperClass);
    }

}
