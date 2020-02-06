package org.laniakeamly.poseidon.framework.beans;

import javassist.ClassPool;
import org.laniakeamly.poseidon.extension.ConnectionPool;
import org.laniakeamly.poseidon.framework.limit.Resource;
import org.laniakeamly.poseidon.framework.limit.Valid;
import org.laniakeamly.poseidon.framework.cache.CacheRefreshTimer;
import org.laniakeamly.poseidon.framework.cache.PoseidonCache;
import org.laniakeamly.poseidon.framework.db.JdbcSupport;
import org.laniakeamly.poseidon.framework.db.NativeResult;
import org.laniakeamly.poseidon.framework.cache.PoseidonCacheImpl;
import org.laniakeamly.poseidon.framework.exception.runtime.BeansManagerException;
import org.laniakeamly.poseidon.framework.timer.Timer;
import org.laniakeamly.poseidon.framework.timer.TimerManager;
import org.laniakeamly.poseidon.framework.tools.ReflectUtils;
import org.laniakeamly.poseidon.framework.tools.StringUtils;
import org.laniakeamly.poseidon.framework.db.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * ioc容器对象
 * 这个容器只适用于Poseidon
 *
 * ioc container object.
 * this container just be applicable for poseidon.
 *
 * Copyright: Create by TianSheng on 2019/11/28 17:25
 *
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 *
 */
public class BeansManager {

    private static Map<String, Object> beans = new HashMap<>();

    @Resource
    private NativeJdbc newNativeJdbc() {
        return new NativeJdbcImpl();
    }

    public static NativeResult newNativeResult() {
        return new NativeResultMysql();
    }

    @Resource(name = "jdbc")
    private JdbcSupport newJdbcSupport() {
        return new JdbcSupportImpl();
    }

    @Resource(name = "cache")
    private PoseidonCache newPoeseidonCache() {
        PoseidonCache cache = new PoseidonCacheImpl();
        Timer timer = new CacheRefreshTimer(cache);
        TimerManager.getManager().submit(timer);
        return cache;
    }

    @Resource(name = "pool")
    private ConnectionPool newConnectionPool() {
        return new org.laniakeamly.poseidon.framework.pool.ConnectionPool();
    }

    @Resource(name = "classPool")
    private ClassPool getClassPool() throws ClassNotFoundException {
        ClassPool pool = ClassPool.getDefault();
        // pool.importPackage("org.laniakeamly.poseidon.framework.loader.Parameter");
        return pool;
    }

    // get bean
    public static <T> T getBean(String name) {
        T instance = (T) factory(name);
        return instance;
    }

    private static Object factory(String name) {
        try {
            Object bean = beans.get(name);
            if (bean != null) return bean;
            Class<?> target = BeansManager.class;
            Object instance = target.newInstance();
            Method[] methods = target.getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Resource.class)) {
                    String aname = method.getDeclaredAnnotation(Resource.class).name();
                    if (name.equals(aname)) {
                        put(name, ReflectUtils.invoke(method,instance));
                        return beans.get(name);
                    }
                    String ReturnName = method.getReturnType().getName();
                    ReturnName = ReturnName.substring(ReturnName.lastIndexOf(".") + 1);
                    if (name.equals(ReturnName)) {
                        put(name, ReflectUtils.invoke(method,instance));
                        return beans.get(name);
                    }

                }
            }
            throw new BeansManagerException("bean name \"" + name + "\" is not found");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void put(String name, Object value) {
        beans.put(name, inject(value));
    }

    /**
     * 对象中是存在需要注入的成员
     *
     * @param object 目标对象
     * @return
     */
    private static Object inject(Object object) {
        try {
            Class<?> target = object.getClass();
            Field[] fields = target.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(Valid.class)) {
                    Valid valid = field.getDeclaredAnnotation(Valid.class);
                    String name = valid.name();
                    if (StringUtils.isEmpty(name)) {
                        String typeName = field.getType().getTypeName();
                        typeName = typeName.substring(typeName.lastIndexOf(".") + 1);
                        Object inject = factory(typeName);
                        field.set(object, inject);
                    } else {
                        Object inject = factory(name);
                        field.set(object, inject);
                    }
                }
            }
            return object;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
