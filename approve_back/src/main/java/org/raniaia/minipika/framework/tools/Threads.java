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
 * Creates on 2020/3/24.
 */

import lombok.SneakyThrows;

/**
 * 静态{@code Thread}工具。
 *
 * @author tiansheng
 */
public class Threads {

    /**
     * 获取方法的调用者。
     */
    @SneakyThrows
    public static Class<?> getCaller() {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        String classname = elements[elements.length - 1].getClassName();
        return Class.forName(classname);
    }

    /**
     * 获取调用者的类加载器
     * @return
     */
    public static ClassLoader getCallerLoader() {
        return getCaller().getClassLoader();
    }

}
