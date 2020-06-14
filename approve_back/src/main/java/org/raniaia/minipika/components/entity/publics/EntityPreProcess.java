package org.jiakesiws.minipika.components.entity.publics;

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
 * Creates on 2020/2/8.
 */

import javassist.*;
import lombok.Getter;
import lombok.Setter;
import org.jiakesiws.minipika.framework.provide.entity.Entity;
import org.jiakesiws.minipika.framework.provide.entity.Norm;
import org.jiakesiws.minipika.components.pool.MinipikaClassPool;
import org.jiakesiws.minipika.framework.tools.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Norm注解处理器
 *
 * 处理过程是先获取到注解然后再根据注解生成{@link NormProperties}属性
 * 最后根据生成的{@link NormProperties}对类的字节码做动态修改来达到最终效果。
 *
 * Norm annotation processor.
 *
 * this processor first get {@link Norm} then create
 * {@link NormProperties} properties.
 *
 * end according to {@link NormProperties} modify class bytecode.
 * @author tiansheng
 */
public class EntityPreProcess {

    private List<NormProperties> properties;
    private ClassLoader loader = getClass().getClassLoader();
    private final ClassPool classPool = new MinipikaClassPool(true);
    private final String superClasspath = "org.raniaia.minipika.components.entity.publics.AbstractEntity";
    private final String methodJavaCode = "" +
            "{" +
            "if(" +
            "!org.raniaia.minipika.framework.tools." +
            "NormUtils.getInstanceSave().matches($1," +
            "org.raniaia.minipika.components.config.GlobalConfig.getConfig().getNorm(\"{}\"))" +
            "){super.canSave=false;}" +
            "}";

    public EntityPreProcess(String[] packages) throws Exception {
        properties = getNormProperties(packages);
    }

    /**
     * 通过修改字节码重写Setter方法
     */
    public void modifySetter() {
        if (properties == null) return;
        try {
            for (NormProperties property : properties) {
                CtClass clazz = property.getCtClass();
                clazz.defrost();
                // 修改Setter方法
                Map<String, Norm> fields = property.getFields();
                for (Map.Entry<String, Norm> entry : fields.entrySet()) {
                    String entryKey = entry.getKey();
                    String methodName = "set".concat(StringUtils.upperCase(entryKey, 1));
                    CtMethod ctMethod = clazz.getDeclaredMethod(methodName);
                    ctMethod.setBody(StringUtils.format(methodJavaCode, entry.getValue().value(), entryKey));
                    ctMethod.insertAfter(StringUtils.format("{} = $1;", entryKey));
                }
                // 重新编译并加载新的Entity类
                String classpath = clazz.getName();
                String filedir = loader.getResource(".").toString().substring(6);
                clazz.writeFile(filedir);
                loader.loadClass(classpath);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Getter
    @Setter
    class NormProperties {

        /**
         * entity class path.
         */
        private String classpath;

        private CtClass ctClass;

        /**
         * fields and {@link Norm} annotations
         */
        private Map<String, Norm> fields;

        public NormProperties() {
        }

        public NormProperties(String classpath, CtClass ctClass) {
            this.ctClass = ctClass;
            this.classpath = classpath;
        }

    }

    /**
     * 获取扫描到的Norm属性
     * @throws ClassNotFoundException
     */
    private List<NormProperties> getNormProperties(String[] packages) throws Exception {
        if (packages == null) return null;
        List<NormProperties> normPropertiesList = new ArrayList<>();
        for (String aPackage : packages) {
            CtClass[] ctClasses = ((MinipikaClassPool) classPool).getCtClassArray(aPackage);
            for (CtClass aClass : ctClasses) {
                if (aClass.getAnnotation(Entity.class) != null) {
                    String filedir = loader.getResource(".").toString().substring(6);
                    CtClass superClass = classPool.get(superClasspath);
                    // 给Entity类添加一个父类
                    aClass.setSuperclass(superClass);
                    aClass.writeFile(filedir);
                }
                NormProperties properties = null;
                Map<String, Norm> hashMap = null;
                CtField[] fields = aClass.getDeclaredFields();
                for (CtField field : fields) {
                    Object[] annotations = field.getAnnotations();
                    for (Object annotation : annotations) {
                        if (annotation instanceof Norm) {
                            Norm norm = (Norm) annotation;
                            if (hashMap == null) hashMap = new HashMap<>();
                            hashMap.put(field.getName(), norm);
                        }
                    }
                }
                if (hashMap != null) {
                    String classname = aClass.getPackageName().concat(aClass.getName());
                    properties = new NormProperties(classname, aClass);
                    properties.setFields(hashMap);
                    normPropertiesList.add(properties);
                }
            }
        }

        return normPropertiesList.isEmpty() ? null : normPropertiesList;
    }

}
