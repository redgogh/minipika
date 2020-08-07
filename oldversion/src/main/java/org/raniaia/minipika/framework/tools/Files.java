package org.jiakesimk.minipika.framework.tools;

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
 * Creates on 2020/3/12.
 */

import lombok.SneakyThrows;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Static {@code File} tools class.
 */
public class Files {

    /**
     * 创建一个可变的{@code File}对象示例。
     * 根据 <b>path#string</b>去创建。
     */
    public static File newFile(String path) {
        return new File(Paths.toClasspath(path));
    }

    /**
     * 创建一个可变的{@code File}对象示例。
     * 根据 <b>uri#URI</b>去创建。
     */
    public static File newFile(URI uri) {
        return new File(uri);
    }

    /**
     * 创建一个可变的{@code File}对象示例。
     * 根据 < <b>url#URL</b>去创建。
     */
    @SneakyThrows
    public static File newFile(URL url) {
        return newFile(url.toURI());
    }

    /**
     * 通过{@link InputStream}获取文件数据，返回{@link String}类型。
     */
    public static String read(InputStream input) {
        if (input == null) throw new NullPointerException();
        StringBuilder out = new StringBuilder();
        try {
            final int size = 1024;
            final char[] buffer = new char[size];
            Reader reader = new InputStreamReader(input, StandardCharsets.UTF_8);
            while (true) {
                int rsz = reader.read(buffer, 0, size);
                if (rsz < 0) break;
                out.append(buffer, 0, rsz);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toString();
    }

    /**
     * 读取文件数据根据文件路径去获取，返回{@link String}类型。
     */
    public static String read(String path) {
        try {
            return read(new FileInputStream(Paths.toClasspath(path)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取文件数据根据{@link URL}去获取，返回{@link String}类型。
     */
    public static String read(URL url) {
        return read(url.toExternalForm().replace("file:/", ""));
    }

    /**
     * 将数据写入指定的文件。并返回{@code boolean}
     * true: 写入成功，false: 写入失败。
     */
    public static boolean write(String path, String content) {
        path = Paths.toClasspath(path);
        FileWriter fileWriter = null;
        try {
            fileWriter = newFileWrite(path, true);
            fileWriter.write(content);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 创建一个新的{@code FileWrite}对象实例。该方法将判断路径是否存在。如果不存在，将创建目录和文件。
     */
    @SneakyThrows
    public static FileWriter newFileWrite(String path, String filename) {
        path = Paths.toClasspath(path);
        File file0 = new File(path);
        if (!file0.exists()) {
            file0.mkdirs();
        }
        File file = new File(path.concat("/").concat(filename));
        if (!file.exists()) {
            file.createNewFile();
        }
        return new FileWriter(file);
    }

    /**
     * 按路径创建一个新的{@code FileWrite}对象实例，并{@code append}。
     */
    @SneakyThrows
    public static FileWriter newFileWrite(String path, boolean append) {
        return new FileWriter(Paths.toClasspath(path), append);
    }

    /**
     * 通过{@code File}实例创建新的{@code FileWrite}对象实例。
     */
    @SneakyThrows
    public static FileWriter newFileWrite(File file) {
        return new FileWriter(file);
    }

    /**
     * 通过{@code File}实例创建新的{@code FileInputStream}对象实例。
     */
    @SneakyThrows
    public static FileInputStream newFileInputStream(File file) {
        return new FileInputStream(file);
    }

    /**
     * 通过{@code path}实例创建新的{@code FileInputStream}对象实例。
     */
    @SneakyThrows
    public static FileInputStream newFileInputStream(String path) {
        return new FileInputStream(Paths.toClasspath(path));
    }

    /**
     * 通过{@code path}实例创建新的{@code FileInputStream}对象实例。
     */
    @SneakyThrows
    public static FileInputStream newFileInputStream(String path, Class<?> target) {
        return new FileInputStream(Paths.toClasspath(path, target));
    }

    /**
     * 通过{@code path}实例创建新的{@code FileInputStream}对象实例。
     */
    @SneakyThrows
    public static FileInputStream newFileInputStream(String path, ClassLoader loader) {
        return new FileInputStream(Paths.toClasspath(path, loader));
    }

    /**
     * 获取文件名后缀
     */
    public static String getSuffix(File file) {
        return getSuffix(file.getName());
    }

    /**
     * 根据文件名称获取文件后缀名
     */
    public static String getSuffix(String name) {
        int indexof = name.lastIndexOf(".");
        return indexof == -1 ? null : name.substring(indexof + 1);
    }

    /**
     * 将文件转换成字节码
     * @param file
     * @return
     */
    public static byte[] fileToBytes(File file) {
        try {
            FileInputStream in = newFileInputStream(file);
            //当文件没有结束时，每次读取一个字节显示
            byte[] data = new byte[in.available()];
            in.read(data);
            in.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将字节码转换成文件
     *
     * @param file
     * @param bytes
     */
    public static void bytesToFile(File file,byte[] bytes) {
        try{
            FileOutputStream out = new FileOutputStream(file);
            out.write(bytes);
            out.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
