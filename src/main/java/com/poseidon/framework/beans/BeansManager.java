package com.poseidon.framework.beans;

import com.poseidon.framework.annotation.Assembly;
import com.poseidon.framework.annotation.Valid;
import com.poseidon.framework.db.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

/**
 * 管理接口的创建，类似于spring的bean，只不过不是注解形式
 * Create by 2BKeyboard on 2019/11/28 17:25
 */
public class BeansManager {

    private static Map<String,Object> beans;

    public static NativeJdbc newNativeJdbc() {
        return new NativeJdbcImpl();
    }

    public static JdbcSupport newJdbcSupport(){
        return JdbcSupport.getTemplate();
    }

    public static NativeResult newNativeResult() {
        return new NativeResultMysql();
    }

    public static NativeResult newNativeResult(ResultSet resultSet) {
        return newNativeResult().build(resultSet);
    }

    public static void inject(){
        try {
            if (beans == null) {
                beans = new HashMap<>();
                getBeans();
            }
            String className = Thread.currentThread().getStackTrace()[2].getClassName();
            Class<?> target = Class.forName(className);
            Object instance = target.newInstance();

            for (Field field : target.getDeclaredFields()) {
                if (field.isAnnotationPresent(Assembly.class)) {
                    field.setAccessible(true);
                    field.set(instance, beans.get(field.getType().getName()));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void getBeans(){
        try {
            BeansManager manager = new BeansManager();
            Class<?> target = BeansManager.class;
            Method[] methods = target.getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Valid.class)) {
                    beans.put(method.getAnnotatedReturnType().getType().getTypeName(),method.invoke(manager));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
