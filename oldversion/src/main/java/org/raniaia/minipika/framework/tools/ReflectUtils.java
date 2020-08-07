package org.jiakesiws.minipika.framework.tools;

/* ************************************************************************
 *
 * Copyright (C) 2020 2B键盘 All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ************************************************************************/

/*
 * Creates on 2019/12/20.
 */

import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedList;
import java.util.List;

/**
 * 反射工具类
 * @author 2B键盘
 * @email jiakesiws@gmail.com
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
     * 获取方法参数，根据class路径来获取{@link String}
     * @param classpath
     * @param methodName
     * @param parameterTypes
     * @return
     */
    public static String[] displayParametersMetadata(String classpath,
                                                     String methodName,
                                                     Class<?>... parameterTypes) {
        try {
            Class<?> clazz = Class.forName(classpath);
            return displayParametersMetadata(clazz, methodName, parameterTypes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取方法参数名称，根据{@link Class}对象获取
     * @param clazz
     * @param methodName
     * @param parameterTypes
     * @return 方法参数名称数组，按照顺序获取。
     */
    @SuppressWarnings("unchecked")
    public static String[] displayParametersMetadata(Class<?> clazz,
                                                     String methodName,
                                                     Class<?>... parameterTypes) throws NoSuchMethodException {
        if (clazz == null) return null;
        Method method = null;
        if (parameterTypes.length > 0) {
            method = clazz.getMethod(methodName, parameterTypes);
        } else {
            Method[] methodArray = clazz.getMethods();
            for (Method method1 : methodArray) {
                if (methodName.equals(method1.getName())) {
                    method = method1;
                    break;
                }
            }
        }
        List<String> nameList = new LinkedList();
        for (final Parameter parameter : method.getParameters()) {
            nameList.add(parameter.getName());
        }
        String[] nameArray = new String[nameList.size()];
        nameList.toArray(nameArray);
        return nameArray;
    }

    /**
     * 获取成员名
     * @param o the object
     * @return String[]
     */
    public static String[] getMemberName(Object o) {
        String[] name;
        Class<?> clazz = o.getClass();
        Field[] fields = clazz.getDeclaredFields();
        name = new String[fields.length];
        for (int i = 0; i < fields.length; i++)
            name[i] = fields[i].getName();
        return name;
    }

    /**
     * 获取成员变量的值
     * @param obj
     * @param memberName
     * @return
     */
    public static Object getMemberValue(Object obj, String memberName) {
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

    /**
     * 设置成员变量的值
     * @param obj
     * @param memberName
     * @return
     */
    public static void setMemberValue(Object obj, String memberName,Object value) {
        try {
            Class<?> target = obj.getClass();
            Field field = target.getDeclaredField(memberName);
            field.setAccessible(true);
            field.set(obj,value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public static <T> T newInstance(Class<T> c){
        return c.newInstance();
    }

}
