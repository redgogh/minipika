package org.jiakesiws.minipika.framework.sql;

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

/*
 * Creates on 2019/12/23.
 */

import javassist.*;
import org.jiakesiws.minipika.framework.loader.NativeClassLoader;
import org.jiakesiws.minipika.components.pool.MinipikaClassPool;
import org.jiakesiws.minipika.framework.provide.Minipika;
import org.jiakesiws.minipika.framework.sql.xml.build.PrecompiledMethod;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * 编译解析后的Java代码
 * @author tiansheng
 */
public class Converter {

    @Inject
    private MinipikaClassPool pool;

    @SuppressWarnings("deprecation")
    public void conversion(PrecompiledMethod methodValue, Map<String, Object> parameter, String fullClassName) throws Exception {
        String methodString = process(methodValue.getMethodString(), parameter);
        CtClass ctClass = null;
        try {
            ctClass = pool.get(fullClassName);
        } catch (Exception e) {
            if(e instanceof NotFoundException) {
                ctClass = pool.makeClass(fullClassName);
            }else{
                e.printStackTrace();
            }
        }
        ctClass.defrost();
        CtMethod ctMethod = CtNewMethod.make(methodString, ctClass);
        ctClass.addMethod(ctMethod);
        NativeClassLoader classLoader = new NativeClassLoader(); // 类加载器
        Class<?> target = classLoader.findClassByBytes(fullClassName, ctClass.toBytecode());
        Object object = target.newInstance();
        Method iMethod = object.getClass().getDeclaredMethod(methodValue.getName(), Map.class, List.class);
        methodValue.setExecute(object);
        methodValue.setIMethod(iMethod);
    }

    /**
     * 处理java代码中的List
     * @param strValue
     * @param parameter
     * @return
     */
    private String process(String strValue, Map<String, Object> parameter) {
        String method = strValue;
        for (Map.Entry<String, Object> entry : parameter.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof List) {
                List listValue = (List) value;
                if (!listValue.isEmpty()) {
                    value = listValue.get(0).getClass().getName();
                }
            }
            String valueName = "java.lang.Object";
            if (value != null) {
                valueName = value.getClass().getName();
            }
            method = method.replaceAll("#".concat(key).concat("#"), valueName);
        }
        method = method.replaceAll("#.*?#","java.lang.Object");
        return method;
    }

}
