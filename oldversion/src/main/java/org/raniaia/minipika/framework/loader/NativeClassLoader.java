package org.jiakesimk.minipika.framework.loader;

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
 * Creates on 2019/12/12.
 */

import java.lang.reflect.Field;

/**
 * @author tiansheng
 */
public class NativeClassLoader extends ClassLoader{

    public NativeClassLoader(){}

    /**
     * 根据字节码来加载类
     */
    public Class<?> findClassByBytes(String name,byte[] classBytes){
        return defineClass(name,classBytes,0,classBytes.length);
    }

    /**
     * 复制对象的所有属性并返回一个新的对象
     * @param target
     * @param src
     * @return
     */
    public Object getObject(Class<?> target,Object src){
        try {
            Object instance = target.getDeclaredConstructor().newInstance();
            Field[] fields = src.getClass().getDeclaredFields();
            for (Field oldField : fields) {
                String fieldName = oldField.getName();
                oldField.setAccessible(true);
                Field newInstanceField = instance.getClass().getDeclaredField(fieldName);
                newInstanceField.setAccessible(true);
                newInstanceField.set(instance,oldField.get(src));
            }
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
