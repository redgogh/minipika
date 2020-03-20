package org.raniaia.poseidon.framework.context;

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
 * Creates on 2020/3/20 0:48
 */

import lombok.SneakyThrows;
import org.raniaia.available.classes.ClassResources;
import org.raniaia.available.list.Lists;
import org.raniaia.poseidon.framework.context.component.BaseModuleAdapter;
import org.raniaia.poseidon.framework.context.component.ModuleRegister;
import org.raniaia.poseidon.framework.provide.component.Export;
import org.raniaia.poseidon.framework.tools.Maps;
import org.raniaia.poseidon.components.This0;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Modules static manager tool, each module must registers with {@code ModuleManager}.
 *
 * If not register with {@code ModuleManager}, then other module cannot instantiate module exposed
 * interface.
 *
 * @author tiansheng
 */
class ComponentManager {

    private static Map<Class<?>,Object> modules = Maps.newHashMap();

    static {
        try {
            List<Class<?>> moduleExportClasses = Lists.newArrayList();
            File currentModuleFile = ClassResources.caller(This0.class).toFile();
            getBaseModuleAdapter(currentModuleFile, moduleExportClasses);
            for (Class<?> moduleExportClass : moduleExportClasses) {
                ModuleRegister register = (ModuleRegister) moduleExportClass.newInstance();
                register.register(modules);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @SneakyThrows
    private static void getBaseModuleAdapter(File modules, List<Class<?>> classes) {
        for (File file : modules.listFiles()) {
            if (file.isDirectory()) {
                getBaseModuleAdapter(file, classes);
                continue;
            }
            String name = file.getName();
            if (".class".equals(name.substring(name.lastIndexOf(".")))) {
                String classpath = file.getPath();
                classpath = classpath.substring(classpath.lastIndexOf("org"));
                classpath = classpath.substring(0,classpath.indexOf(".class")).replaceAll("\\\\",".");
                Class<?> clazz = Class.forName(classpath);
                if(clazz.isAnnotationPresent(Export.class)){
                    classes.add(clazz);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getMODULE0(Class<? extends BaseModuleAdapter> clazz) {
        return (T) modules.get(clazz);
    }

}
