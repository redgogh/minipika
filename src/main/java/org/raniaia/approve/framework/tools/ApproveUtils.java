package org.raniaia.approve.framework.tools;

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
 * Creates on 2019/11/12.
 */

import org.raniaia.available.Available;
import org.raniaia.approve.components.config.GlobalConfig;

import java.io.*;
import java.security.MessageDigest;
import java.util.*;

/**
 * @author tiansheng
 */
public final class ApproveUtils {

    /**
     * 获取Pte文件的File对象
     * @return List
     */
    public static List<File> getMapperXMLs() {
        List<File> mappers = new LinkedList<>();
        String basePackages[] = GlobalConfig.getConfig().getMapperBasePackage();
        for (String basePackage : basePackages) {
            getXMLs(basePackage, mappers);
        }
        return mappers;
    }

    private static void getXMLs(String basePackage, List<File> files) {
        //扫描编译好的所有类路径
        String path = Available.toClasspath(basePackage);
        if (StringUtils.isEmpty(path)) return;
        //将url转换为文件类型
        File dir = new File(path);
        for (File file : dir.listFiles()) {
            //判断file是否为一个文件目录
            if (file.isDirectory()) {
                //如果是一个文件目录就递归再往下读取
                getXMLs(basePackage + "." + file.getName(), files);
            } else {
                files.add(file);
            }
        }
    }

    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 讲字符串加密成md5
     *
     * @param input
     * @return
     */
    public static String encryptToMd5(String input) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(input.getBytes("UTF8"));
            byte s[] = m.digest();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < s.length; i++) {
                builder.append(Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6));
            }
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
