package org.jiakesimk.minipika;

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
 * Creates on 2020/3/21.
 */

import lombok.Getter;
import lombok.SneakyThrows;

import org.jiakesimk.minipika.framework.provide.component.Component;
import org.jiakesimk.minipika.framework.tools.Annotations;
import org.jiakesimk.minipika.framework.tools.ClassResources;
import org.jiakesimk.minipika.framework.tools.Lists;
import org.jiakesimk.minipika.framework.tools.Threads;

import java.io.File;
import java.util.List;

/**
 * Scan components,
 * @author 2B键盘
 * @email jiakesiws@gmail.com
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
