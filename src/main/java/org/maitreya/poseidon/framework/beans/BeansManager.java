package org.maitreya.poseidon.framework.beans;

import org.maitreya.poseidon.customize.ConnectionPool;
import org.maitreya.poseidon.framework.annotation.Resource;
import org.maitreya.poseidon.framework.annotation.Valid;
import org.maitreya.poseidon.framework.cache.CacheRefreshTimer;
import org.maitreya.poseidon.framework.cache.PoseidonCache;
import org.maitreya.poseidon.framework.db.JdbcSupport;
import org.maitreya.poseidon.framework.db.NativeResult;
import org.maitreya.poseidon.framework.cache.PoseidonCacheImpl;
import org.maitreya.poseidon.framework.exception.BeansManagerException;
import org.maitreya.poseidon.framework.timer.Timer;
import org.maitreya.poseidon.framework.timer.TimerManager;
import org.maitreya.poseidon.framework.tools.StringUtils;
import javassist.ClassPool;
import org.maitreya.poseidon.framework.db.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * IOC
 * Create by 2BKeyboard on 2019/11/28 17:25
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
        return new org.maitreya.poseidon.framework.pool.ConnectionPool();
    }

    @Resource(name = "classPool")
    private ClassPool getClassPool(){
        return ClassPool.getDefault();
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
                        put(name, method.invoke(instance));
                        return beans.get(name);
                    }
                    String ReturnName = method.getReturnType().getName();
                    ReturnName = ReturnName.substring(ReturnName.lastIndexOf(".") + 1);
                    if (name.equals(ReturnName)) {
                        put(name, method.invoke(instance));
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
