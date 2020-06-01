package org.raniaia.minipika;

/*
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
 */

/*
 * Creates on 2020/3/21.
 */

import lombok.Getter;
import lombok.SneakyThrows;
import org.raniaia.available.classes.ClassResources;
import org.raniaia.available.list.Lists;
import org.raniaia.available.reflect.Annotations;
import org.raniaia.minipika.framework.provide.component.Component;

import java.io.File;
import java.util.List;

/**
 * Scan components,
 * @author tiansheng
 */
public class ComponentScan {

    @Getter
    private static final File componentPackage = ClassResources.caller(ComponentScan.class).toFile("components");

    @SneakyThrows
    private void scan(File file0, List<Class<?>> classList) {
        for (File file : file0.listFiles()) {
            if (file.isDirectory()) {
                scan(file, classList);
                continue;
            }
            String filename = file.getPath();
            if (".class".equals(filename.substring(filename.lastIndexOf(".")))) {
                filename = filename.replaceAll("\\\\", ".")
                        .replaceAll(".class", "");
                filename = filename.substring(filename.lastIndexOf("org"));
                // new class
                Class<?> clazz = Class.forName(filename);
                Component valid = Annotations.isAnnotation(clazz, Component.class);
                if (valid != null) {
                    classList.add(clazz);
                }
            }
        }
    }

    public List<Class<?>> components(){
        List<Class<?>> classList = Lists.newArrayList();
        scan(componentPackage, classList);
        return classList;
    }

}
