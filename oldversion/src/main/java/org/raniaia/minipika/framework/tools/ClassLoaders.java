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
 * Creates on 2020/3/13
 */

import java.net.URL;

/**
 * 静态类加载器工具类
 */
public class ClassLoaders {

    /**
     * 通过类加载器去查找所需要的资源文件, 传入类对象
     */
    public static URL getResource(String name,Class<?> clazz){
        return clazz.getClassLoader().getResource(name);
    }

    /**
     * 通过类加载器去查找所需要的资源文件, 默认使用对象的调用者。
     */
    public static URL gerResource(String name){
        return getResource(name, Threads.getCaller());
    }

}
