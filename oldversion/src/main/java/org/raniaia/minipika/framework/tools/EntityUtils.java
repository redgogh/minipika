package org.jiakesimk.minipika.framework.tools;

/* ************************************************************************
 *
 * Copyright (C) 2020 tiansheng All rights reserved.
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
 * Creates on 2020/2/15.
 */

import org.jiakesimk.minipika.framework.provide.entity.Ignore;
import org.jiakesimk.minipika.framework.provide.entity.Entity;
import org.jiakesimk.minipika.components.config.GlobalConfig;
import org.jiakesimk.minipika.framework.exception.MinipikaException;
import org.jiakesimk.minipika.framework.exception.EntityException;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author tiansheng
 */
public class EntityUtils {

    /**
     * 获取Entity名
     *
     * @param target
     * @return
     */
    public static String getEntityValue(Class<?> target) {
        return getEntityAnnotation(target).value();
    }

    /**
     * 获取Entity注解
     *
     * @param target
     * @return
     */
    public static Entity getEntityAnnotation(Class<?> target) {
        try {
            if (SecurityManager.existEntity(target)) {
                String prefix = GlobalConfig.getConfig().getTablePrefix();
                Entity anno = target.getDeclaredAnnotation(Entity.class);
                String value = anno.value();
                if (!StringUtils.isEmpty(prefix)) {
                    if (value.length() <= prefix.length() || !value.substring(0, prefix.length()).equals(prefix)) {
                        InvocationHandler invocationHandler = Proxy.getInvocationHandler(anno);
                        Field values = invocationHandler.getClass().getDeclaredField("memberValues");
                        values.setAccessible(true);
                        Map memberValues = (Map) values.get(invocationHandler);
                        memberValues.put("value", GlobalConfig.getConfig().getTablePrefix() + "_" + value);
                    }
                }
                return anno;
            } else {
                throw new MinipikaException("@Entity Not Found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取Entity中的成员，不包含Ignore注解和static修饰的
     * @param o     从{@code o}中获取成员值
     * @return
     */
    public static List<Field> getEntityField(Object o) {
        List<Field> values = Lists.newArrayList();
        try {
            Class<?> target = o.getClass();
            for (Field field : target.getDeclaredFields()) {
                field.setAccessible(true);
                if (!field.isAnnotationPresent(Ignore.class)
                        && !Modifier.isStatic(field.getModifiers())
                        && !Modifier.isFinal(field.getModifiers())) {
                    values.add(field);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return values;
    }

    /**
     * 获取Entity类中所有成员
     * @param target
     * @return
     */
    public static List<Field> getEntityField(Class<?> target) {
        try {
            return getEntityField(target.newInstance());
        } catch (Throwable e) {
            // 一般newInstance异常都是没有声明无参构造器造成的。
            throw new EntityException("entity newInstance error: please check your entity does it exist No-argument constructor.");
        }
    }

    /**
     * 驼峰转下划线
     *
     * @param string
     * @return
     */
    public static String humpToUnderline(String string) {
        StringBuilder builder = new StringBuilder(string);
        int temp = 0; // 定位
        for (int i = 0, len = string.length(); i < len; i++) {
            if (Character.isUpperCase(string.charAt(i))) {
                builder.insert(i + temp, "_");
                temp++;
            }
        }
        return builder.toString().toLowerCase();
    }

    /**
     * 下划线转驼峰
     *
     * @param string
     * @return
     */
    public static String UnderlineToHump(String string) {
        StringBuilder builder = new StringBuilder();
        String[] strs = string.split("_");
        builder.append(strs[0]);
        for (int i = 1; i < strs.length; i++) {
            StringBuilder v = new StringBuilder(strs[i]);
            v.replace(0, 1, String.valueOf(v.charAt(0)).toUpperCase());
            builder.append(v);
        }
        return builder.toString();
    }

    /**
     * 获取所有Entity对象
     *
     * @return
     */
    public static List<Class<?>> getEntitys() {
        List<Class<?>> entitys = new LinkedList<>();
        String[] basePackages = GlobalConfig.getConfig().getEntityPackage();
        if(basePackages==null) return null;
        try {
            for (String basePackage : basePackages) {
                getEntitys(basePackage, entitys);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return entitys;
    }

    private static void getEntitys(String basePackage, List<Class<?>> entitys) throws ClassNotFoundException {
        //扫描编译好的所有类路径
        URL url = MinipikaUtils.class.getResource("/" + basePackage.replaceAll("\\.", "/"));
        if (url == null) return;
        //将url转换为文件类型
        File dir = new File(url.getFile());
        for (File file : dir.listFiles()) {
            //判断file是否为一个文件目录
            if (file.isDirectory()) {
                //如果是一个文件目录就递归再往下读取
                getEntitys(basePackage + "." + file.getName(), entitys);
            } else {
                String classes = basePackage + "." + file.getName();
                if ("class".equals(classes.substring(classes.lastIndexOf(".") + 1))) {
                    classes = classes.replace(".class", "");
                    entitys.add(Class.forName(classes));
                }
            }
        }
    }

}
