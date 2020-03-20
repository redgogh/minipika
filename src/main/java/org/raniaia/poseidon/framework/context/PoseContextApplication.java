package org.raniaia.poseidon.framework.context;

import org.raniaia.poseidon.framework.context.component.BaseModuleAdapter;
import org.raniaia.poseidon.components.log.Log;
import org.raniaia.poseidon.components.log.LogAdapter;

/**
 *
 * Public beans manager object.
 *
 * <p/>
 * License: <a href="https://github.com/Laniakeamly/poseidon/blob/master/LICENSE">Apache License 2.0</a>
 * <p/>
 * Copyright: Create by tiansheng on 2019/12/17 18:29
 * @author tiansheng
 * @version 1.0.0
 * @since 1.8
 */
public final class PoseContextApplication {

    static class Lock {}
    static final Lock lock = new Lock();
    private static PoseBeansManager0 beansManager = PoseBeansManager0.getInstance();;

    public static PoseBeansManager0 getBeansManager(){
        return beansManager;
    }

    /**
     * get bean
     */
    public static <T> T getBean(String name){
        synchronized (lock) {
            return getBeansManager().getBean(name);
        }
    }

    /**
     * get mapper
     */
    public static <T> T getMapper(Class<T> mapperClass){
        return getBeansManager().getMapper(mapperClass);
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

    private static LogAdapter getLogAdapter(){
        return getMODULE(LogAdapter.class);
    }

    /**
     * get module export objects.
     */
    public static <T> T getMODULE(Class<? extends BaseModuleAdapter> clazz){
        return ComponentManager.getMODULE0(clazz);
    }

}
