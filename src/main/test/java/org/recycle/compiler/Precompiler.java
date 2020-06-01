package org.recycle.compiler;

/*
 * Copyright (C) 2020 Tiansheng All rights reserved.
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
 */



import javassist.*;

import org.raniaia.minipika.BeansManager;
import org.raniaia.minipika.components.pool.MinipikaClassPool;
import org.raniaia.minipika.framework.provide.ProvideVar;
import org.recycle.build.PrecompiledClass;
import org.recycle.build.PrecompiledMethod;
import org.raniaia.minipika.framework.loader.NativeClassLoader;
import org.raniaia.minipika.framework.tools.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 预编译处理
 * Copyright: Create by tiansheng on 2019/12/19 11:18
 */
public class Precompiler {

    private MinipikaClassPool pool = BeansManager.get("classPool");

    /**
     * 加载一个类对象,只加载类信息不加载任何方法
     * @param target
     */
    public void loaderClass(PrecompiledClass target) {
        try {
            CtClass ctClass = pool.makeClass(target.getFullName());
            Class<?> instance = ctClass.toClass();
            target.setIClass(instance);
            // 表示自己已经被加载过了
            target.setLoad(true);
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析并编译方法
     * @param pc
     * @param mapperName
     * @param parameters
     */
    public PrecompiledMethod compilerMethod(PrecompiledClass pc, String mapperName, Map<String, Object> parameters) {
        try {
            PrecompiledMethod pm = pc.getPrecompiledMethod(mapperName);
            String methodString = processStringMethod(pm.toString(), parameters);
            System.out.println(methodString);
            if (true) return pm; // 如果已经编译过了直接返回不再进行下面的操作
            System.out.println(methodString);
            CtClass ctClass = pool.get(pc.getFullName());
            ctClass.defrost();
            CtMethod ctMethod = CtNewMethod.make(methodString, ctClass);
            ctClass.addMethod(ctMethod);
            NativeClassLoader classLoader = new NativeClassLoader(); // 类加载器
            Class<?> target = classLoader.findClassByBytes(pc.getFullName(), ctClass.toBytecode());
            Object object = target.newInstance();
            object = classLoader.getObject(target, object);
            pm.setExecute(object);
            pm.setIMethod(object.getClass().getDeclaredMethod(pm.getName(), Map.class, List.class));
            pm.setLoad(true);
            return pm;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对字符串最后一次处理,将里面的需要替换的字符替换掉,让Java能够识别出来
     * 比如说: 强转字符标识 (#name#) 替换成 (java.lang.String)
     *
     * @param str           方法字符串
     * @param parameters    参数
     */
    private String processStringMethod(String str, Map<String, Object> parameters) {
        List<String> names = new ArrayList<>();
        Pattern pattern = Pattern.compile("#(.*?)#");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            names.add(matcher.group(1));
        }
        // 处理null
        str = str.replaceAll("\\(#null#\\) map.get\\(\"null\"\\)", "null");
        for (String name : names) {
            if (!name.equals("null")) {
                String className = null;
                if (parameters.get(name) instanceof List) {
                    if (!((List) parameters.get(name)).isEmpty()) {
                        className = ((List) parameters.get(name)).get(0).getClass().getName();
                    }
                } else {
                    className = parameters.get(name).getClass().getName();
                }
                str = str.replaceAll(("#" + name + "#"), className);
            }
        }
        // 解析所有变量所在的地方
        boolean isString = false;
        char[] charArray = str.toCharArray();
        StringBuilder builder = new StringBuilder();
        List<Position> positions = new ArrayList<>();
        int endPos = 0;
        int startPos = 0;
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];

            if (c == '"') {
                if (!isString) {
                    isString = true;
                    startPos = i;
                } else {
                    endPos = i;
                    isString = false;
                }
            } else {
                if (isString) {
                    builder.append(c);
                }
            }

            if (c == ';') {

                if (isString == false && builder.length() != 0) {
                    positions.add(new Position(builder.toString(), startPos, endPos, i));
                    StringUtils.clear(builder);
                }
            }
        }
        Collections.reverse(positions);
        builder = new StringBuilder(str);
        for (Position position : positions) {
            List<String> params = new ArrayList<>();
            pattern = Pattern.compile("\\{\\{(.*?)}}");
            matcher = pattern.matcher(position.str);
            int count = 0;
            while (matcher.find()) {
                params.add(matcher.group(1));
                count++;
            }
            if (count <= 0) {
                continue;
            }
            String addParamCode = position.addParams(params);
            String replaced = position.str.replaceAll("\\{\\{(.*?)}}", "?");
            builder.replace(position.semicolonPos, position.semicolonPos + 1, ";".concat(addParamCode));
            builder.replace(position.startPos, position.endPos + 1, "\"".concat(replaced).concat("\""));
        }
        return builder.toString();
    }

    private class Position {
        String str;
        int startPos;
        int endPos;
        int semicolonPos;

        public Position(String str, int startPos, int endPos, int semicolonPos) {
            this.str = str;
            this.startPos = startPos;
            this.endPos = endPos;
            this.semicolonPos = semicolonPos;
        }

        public String addParams(List<String> args) {
            StringBuilder builder = new StringBuilder();
            for (String arg : args) {
                if (!arg.contains(".")) {
                    builder.append(StringUtils.format(ProvideVar.SQL_PARAMS_SET + ".add(map.get(\"{}\"));", arg));
                } else {
                    String[] str = arg.split("\\.");
                    builder.append(StringUtils.format(ProvideVar.SQL_PARAMS_SET +
                            ".add(org.raniaia.minipika.framework.tools.ReflectUtils.getMemberValue(${},\"{}\"));", str[0],str[1]));
                }
            }
            return builder.toString();
        }

    }

}
