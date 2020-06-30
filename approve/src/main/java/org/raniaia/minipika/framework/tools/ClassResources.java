package org.jiakesimk.minipika.framework.tools;

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
 * Creates on 2020/3/20.
 */

import lombok.SneakyThrows;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;

/**
 * 静态{@code ClassLoader＃getResource}工具类。
 */
public class ClassResources {

    Class<?> caller;

    @SneakyThrows
    private ClassResources(){
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        this.caller = Class.forName(stacks[stacks.length-1].getClassName());
    }

    private ClassResources(Class<?> caller){
        this.caller = caller;
    }

    /**
     * 通过{@link ClassLoader＃getResource}获取{@link URL}。
     */
    public URL toURL(){
        return toURL("");
    }

    /**
     * 通过{@link ClassLoader＃getResource}获取{@link URL}。
     */
    public URL toURL(String name){
        return caller.getResource(name);
    }

    /**
     * 通过{@link ClassLoader＃getResource}获取{@link File}。
     */
    public File toFile(){
        return new File(toURL().toString());
    }

    /**
     * 通过{@link ClassLoader＃getResource}获取{@link File}。
     */
    public File toFile(String name){
        return new File(toURL(name).toString());
    }

    public static ClassResources caller(){
        return new ClassResources();
    }

    public static ClassResources caller(Class<?> caller){
        return new ClassResources(caller);
    }

}
