package org.raniaia.poseidon.framework.context;

import javassist.ClassPool;
import org.raniaia.poseidon.extension.ConnectionPool;
import org.raniaia.poseidon.components.log.Log;
import org.raniaia.poseidon.components.log.LogAdapter;
import org.raniaia.poseidon.framework.provide.Resource;
import org.raniaia.poseidon.framework.provide.Valid;
import org.raniaia.poseidon.framework.cache.CacheRefreshTimer;
import org.raniaia.poseidon.framework.cache.PoseidonCache;
import org.raniaia.poseidon.framework.config.GlobalConfig;
import org.raniaia.poseidon.framework.db.JdbcSupport;
import org.raniaia.poseidon.framework.db.NativeResult;
import org.raniaia.poseidon.framework.cache.PoseidonCacheImpl;
import org.raniaia.poseidon.framework.loader.PoseidonClassPool;
import org.raniaia.poseidon.framework.mapper.MapperInvocation;
import org.raniaia.poseidon.framework.timer.Timer;
import org.raniaia.poseidon.framework.timer.TimerManager;
import org.raniaia.poseidon.framework.tools.ReflectUtils;
import org.raniaia.poseidon.framework.tools.StringUtils;
import org.raniaia.poseidon.framework.db.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ioc容器对象
 * 这个容器只适用于Poseidon
 *
 * ioc container object.
 * this container just be applicable for poseidon.
 *
 * Copyright: Create by tiansheng on 2019/11/28 17:25
 *
 * @author tiansheng
 * @version 1.0.0
 * @since 1.8
 *
 */
@Deprecated
@SuppressWarnings({"unchecked"})
public class PoseBeansManager0 implements PoseidonBeansManager {

    Map<String, Object> beans = new ConcurrentHashMap<>();
    Map<String, Object> mapperBeans = new ConcurrentHashMap<>();

    static final Log logger = PoseContextApplication.getLog(PoseBeansManager0.class);

    private volatile static PoseBeansManager0 beansManager;

    public static PoseBeansManager0 getInstance(){
        if(beansManager == null){
            beansManager = new PoseBeansManager0();
        }
        return beansManager;
    }

    @Override
    public void initScanBean() {

    }

    @Override
    public void scanBeans(Class<?>... classes) {

    }

    @Resource
    private NativeJdbc newNativeJdbc() {
        return new NativeJdbcImpl();
    }

    @Resource(name = "jdbc")
    private JdbcSupport newJdbcSupport() {
        return new JdbcSupportImpl();
    }

    @Resource(name = "cache")
    private PoseidonCache newPoseidonCache() {
        PoseidonCache cache = new PoseidonCacheImpl();
        Timer timer = new CacheRefreshTimer(cache);
        TimerManager.getManager().submit(timer);
        return cache;
    }

    @Resource(name = "pool")
    private ConnectionPool newConnectionPool() {
        return new org.raniaia.poseidon.framework.pool.ConnectionPool();
    }

    @Resource(name = "classPool")
    private ClassPool getClassPool() throws ClassNotFoundException {
        PoseidonClassPool pool = new PoseidonClassPool(true);
        return pool;
    }

    /**
     * 创建一个结果集
     */
    public NativeResult newNativeResult() {
        return new NativeResultMysql();
    }

    // get bean
    public <T> T getBean(String name) {
        return (T) factory(name); // (T) beans.get(name);
    }

    // 获取mapper映射对象
    <T> T getMapper(Class<T> clazz) {
        String simpleName = clazz.getSimpleName();
        Object sqlMapper = mapperBeans.get(simpleName);
        if (sqlMapper == null) {
            Object invoker = MapperInvocation.invoker(clazz);
            mapperBeans.put(simpleName, invoker);
            sqlMapper = mapperBeans.get(simpleName);
        }
        return (T) sqlMapper;
    }

    public <T> T putBean(String name, Object beanObject) {
        put(name, beanObject);
        return (T) getBean(name);
    }

    private Object factory(String name) {
        try {
            Object bean = beans.get(name);
            if (bean != null) return bean;
            Class<?> target = PoseBeansManager0.class;
            Object instance = target.newInstance();
            Method[] methods = target.getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Resource.class)) {
                    String aname = method.getDeclaredAnnotation(Resource.class).name();
                    // 如果没开启缓存则不将缓存实例化
                    if ("cache".equals(aname) && !GlobalConfig.getConfig().getCache()) {
                        continue;
                    }
                    if (name.equals(aname)) {
                        put(name, ReflectUtils.invoke(method, instance));
                        return beans.get(name);
                    }
                    String ReturnName = method.getReturnType().getSimpleName();
                    if (name.equals(ReturnName)) {
                        put(name, ReflectUtils.invoke(method, instance));
                        return beans.get(name);
                    }

                }
            }
        } catch (Exception e) {
            logger.error("bean name \"" + name + "\" is not found");
        }
        return null;
    }

    private void put(String name, Object value) {
        beans.put(name, inject(value));
        logger.info("add bean[" + name + "]: " + value.getClass().getName());
    }

    /**
     * 对象中是存在需要注入的成员
     *
     * @param object 目标对象
     * @return
     */
    public Object inject(Object object) {
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
                        Object inject;
                        if ("logger".equals(name)) {
                            inject = ((LogAdapter) beans.get("logAdapter")).getLog(target);
                        } else {
                            inject = factory(name);
                        }
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
