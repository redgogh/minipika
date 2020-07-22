package org.jiakesimk.minipika.framework.tools;

/* ************************************************************************
 *
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
 *
 * ************************************************************************/

/*
 * Creates on 2020/2/7.
 */

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

/**
 * @author tiansheng
 */
public class MinipikaIOUtils {

    /**
     * 获取Resource文件夹下的文件
     * @param name
     * @return
     */
    public static InputStream getResourceAsStream(String name){
        return MinipikaIOUtils.class.getClassLoader().getResourceAsStream(name);
    }

    /**
     * 从输入流中读取String
     * @param input
     * @return
     */
    public static String getStringByInputStream(InputStream input){
        if(input == null) throw new NullPointerException();
        final int size = 1024;
        final char[] buffer = new char[size];
        final StringBuilder out = new StringBuilder();
        try {
            Reader reader = new InputStreamReader(input, "UTF-8");
            while(true){
                int rsz = reader.read(buffer,0,size);
                if(rsz < 0) break;
                out.append(buffer,0,rsz);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return out.toString();
    }

    /**
     * 获取resource目录下的json文件并转换成{@link JSONObject}
     * @param name
     * @return
     */
    public static JSONObject getResourceAsJson(String name){
        return JSONObject.parseObject(getStringByInputStream(getResourceAsStream(name)));
    }

    /**
     * 读取resource目录下的Properties文件
     * 根据{@link InputStream}读取
     *
     * @param input
     * @return
     */
    public static Properties getResourceAsProperties(InputStream input){
        Properties p = null;
        try {
            if(input != null){
                p = new Properties();
                p.load(input);
                return p;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取resource目录下的Properties文件
     * 根据文件名读取
     *
     * @param name
     * @return
     */
    public static Properties getResourceAsProperties(String name){
        return getResourceAsProperties(getResourceAsStream(name));
    }

    /**
     * 将resource下的文件读取成String
     * @param name
     * @return
     */
    public static String getResourceAsString(String name){
        return getStringByInputStream(getResourceAsStream(name));
    }
    
}
