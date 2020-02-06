package org.laniakeamly.poseidon.framework.tools;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射工具类
 * Copyright: Create by TianSheng on 2019/12/20 11:47
 */
public class ReflectUtils {

    /**
     * 方法调用
     * @param method
     * @param obj
     * @param args
     */
    public static Object invoke(Method method, Object obj, Object... args) {
        try {
            method.setAccessible(true);
            return method.invoke(obj, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取成员变量的值
     * @param obj
     * @param memberName
     * @return
     */
    public static Object getMemberValue(Object obj,String memberName){
        try {
            Class<?> target = obj.getClass();
            Field field = target.getDeclaredField(memberName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
