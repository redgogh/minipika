package org.laniakeamly.poseidon.framework.tools;

import org.laniakeamly.poseidon.framework.annotation.Ignore;
import org.laniakeamly.poseidon.framework.annotation.Model;
import org.laniakeamly.poseidon.framework.config.GlobalConfig;
import org.laniakeamly.poseidon.framework.config.PropertiesConfig;
import org.laniakeamly.poseidon.framework.exception.PoseidonException;
import org.laniakeamly.poseidon.framework.exception.runtime.ModelException;
import org.laniakeamly.poseidon.framework.model.SecurityManager;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.security.MessageDigest;
import java.util.*;

/**
 * Poseidon ORM Framework简写POF
 * @author TianSheng
 * @version 1.0.0
 * @date 2019/11/12 0:10
 * @since 1.8
 */
public final class POFUtils {

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
        URL url = POFUtils.class.getResource("/" + basePackage.replaceAll("\\.", "/"));
        if (url == null) return;
        //将url转换为文件类型
        File dir = new File(url.getFile());
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
