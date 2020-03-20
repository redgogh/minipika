package org.raniaia.poseidon.framework.context;

import org.raniaia.poseidon.framework.context.module.BaseModuleAdapter;
import org.raniaia.poseidon.modules.log.Log;
import org.raniaia.poseidon.modules.log.LogAdapter;

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

    /**
     * get bean
     */
    public static <T> T getBean(String name){
        return PoseBeansManager.getBean(name);
    }

    /**
     * get mapper
     */
    public static <T> T getMapper(Class<T> mapperClass){
        return PoseBeansManager.getMapper(mapperClass);
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
        return ModulesManager.getMODULE0(clazz);
    }

}
