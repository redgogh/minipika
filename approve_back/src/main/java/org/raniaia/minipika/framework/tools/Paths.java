package org.raniaia.minipika.framework.tools;

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
 * Creates on 2020/3/28.
 */

import java.util.Objects;

/**
 * 关于路径的一些静态操作。
 * @author tiansheng
 */
public class Paths {

    /**
     * 将'classpath:'字符串替换成当前编译后类文件的根路径。
     */
    public static String toClasspath(String path){
        return toClasspath(path,Threads.getCaller());
    }

    /**
     * 将'classpath:'字符串替换成当前编译后类文件的根路径。
     */
    public static String toClasspath(String path,Class<?> clazz){
        if(Objects.requireNonNull(path,"path cannot null.").contains("classpath:")){
            path = path.replace("classpath:",getClasspath(clazz));
        }
        return StringUtils.delFirst(path.replaceAll("\\\\","/"));
    }

    /**
     * 将'classpath:'字符串替换成当前编译后类文件的根路径。
     */
    public static String toClasspath(String path,ClassLoader loader){
        if(Objects.requireNonNull(path,"path cannot null.").contains("classpath:")){
            path = path.replace("classpath:",getClasspath(loader));
        }
        return path.replaceAll("\\\\","/");
    }

    /**
     * 获取当前classpath的路径
     */
    public static String getClasspath(){
        return getClasspath(Threads.getCallerLoader());
    }

    /**
     * 获取当前classpath的路径
     */
    public static String getClasspath(ClassLoader loader){
        return loader != null
                ? loader.getResource("").getFile()
                : Threads.getCallerLoader().getResource("").getFile();
    }

    /**
     * 获取当前classpath的路径
     */
    public static String getClasspath(Class<?> clazz){
        return clazz != null
                ? clazz.getResource("").getFile()
                : Threads.getCallerLoader().getResource("").getFile();
    }

}
