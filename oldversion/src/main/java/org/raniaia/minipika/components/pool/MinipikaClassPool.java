package org.jiakesiws.minipika.components.pool;

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
 * Creates on 2020/2/8.
 */

import javassist.*;
import org.jiakesiws.minipika.framework.provide.component.Component;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author 2B键盘
 * @email jiakesiws@gmail.com
 */
@Component
public class MinipikaClassPool extends ClassPool {

    public MinipikaClassPool() {
        this(true);
    }

    public MinipikaClassPool(boolean useDefaultPath) {
        super(useDefaultPath);
    }

    public MinipikaClassPool(ClassPool parent) {
        super(parent);
    }

    /**
     * 将某个包下的Class转换成CtClass
     * @param packageName
     * @return
     */
    public CtClass[] getCtClassArray(String packageName) {
        // appendClassPath(new LoaderClassPath(this.getClass().getClassLoader()));
        List<String> classpathList = Lists.newArrayList();
        getFile(packageName, classpathList);
        CtClass[] ctClasses = new CtClass[classpathList.size()];
        for (int i = 0, len = classpathList.size(); i < len; i++) {
            ctClasses[i] = createCtClass(classpathList.get(i), true);
        }
        return ctClasses;
    }

    private void getFile(String basePackage, List<String> fileList) {
        URL url = getClass().getResource("/" + basePackage.replaceAll("\\.", "/"));
        if (url == null) return;
        File dir = new File(url.getFile());
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                getFile(basePackage + "." + file.getName(), fileList);
            } else {
                String classes = basePackage + "." + file.getName();
                if ("class".equals(classes.substring(classes.lastIndexOf(".") + 1))) {
                    classes = classes.replace(".class", "");
                    fileList.add(classes);
                }
            }
        }
    }

}
