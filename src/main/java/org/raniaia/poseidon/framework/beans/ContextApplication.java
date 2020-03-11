package org.raniaia.poseidon.framework.beans;

import org.raniaia.poseidon.framework.log.Log;
import org.raniaia.poseidon.framework.log.LogAdapter;

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
public final class ContextApplication {

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

    /**
     * LogAdapter
     */
    public static LogAdapter getLogAdapter(){
        return BeansManager.getBean("logAdapter");
    }

    /**
     * get log
     */
    public static Log getLog(String key){
        return getLogAdapter().getLog(key);
    }

    /**
     * get log
     */
    public static Log getLog(Class<?> key){
        return getLogAdapter().getLog(key);
    }

}
