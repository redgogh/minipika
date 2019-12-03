package com.tractor.framework.tools;

import com.tractor.framework.annotation.Model;
import com.tractor.framework.config.Config;
import com.tractor.framework.exception.TractorException;
import com.tractor.framework.model.SecurityManager;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.security.MessageDigest;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author 2BKeyboard
 * @version 1.0.0
 * @date 2019/11/12 0:10
 * @since 1.8
 */
public class TractorUtils {

    /**
     * 驼峰转下划线
     *
     * @param string
     * @return
     */
    public static String humpToUnderline(String string) {
        StringBuilder builder = new StringBuilder(string);
        int temp = 0; // 定位
        for (int i = 0, len = string.length(); i < len; i++) {
            if (Character.isUpperCase(string.charAt(i))) {
                builder.insert(i + temp, "_");
                temp++;
            }
        }
        return builder.toString().toLowerCase();
    }

    /**
     * 下划线转驼峰
     *
     * @param string
     * @return
     */
    public static String UnderlineToHump(String string) {
        StringBuilder builder = new StringBuilder();
        String[] strs = string.split("_");
        builder.append(strs[0]);
        for (int i = 1; i < strs.length; i++) {
            StringBuilder v = new StringBuilder(strs[i]);
            v.replace(0, 1, String.valueOf(v.charAt(0)).toUpperCase());
            builder.append(v);
        }
        return builder.toString();
    }

    /**
     * 获取所有Model对象
     *
     * @return
     */
    public static List<Class<?>> getModels() {
        List<Class<?>> models = new LinkedList<>();
        String basePackage = Config.getModelPackage();
        try {
            getModels(basePackage, models);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return models;
    }

    private static void getModels(String basePackage, List<Class<?>> models) throws ClassNotFoundException {
        //扫描编译好的所有类路径
        URL url = TractorUtils.class.getResource("/" + basePackage.replaceAll("\\.", "/"));
        if(url == null) return;
        //将url转换为文件类型
        File dir = new File(url.getFile());
        for (File file : dir.listFiles()) {
            //判断file是否为一个文件目录
            if (file.isDirectory()) {
                //如果是一个文件目录就递归再往下读取
                getModels(basePackage + "." + file.getName(), models);
            } else {
                models.add(Class.forName(basePackage + "." + file.getName().replace(".class", "")));
            }
        }
    }

    /**
     * 获取Model名
     *
     * @param target
     * @return
     */
    public static String getModelValue(Class<?> target) {
        return getModelAnnotation(target).value();
    }

    /**
     * 获取Model注解
     *
     * @param target
     * @return
     */
    public static Model getModelAnnotation(Class<?> target) {
        try {
            if (SecurityManager.existModel(target)) {
                String prefix = Config.getTablePrefix();
                Model anno = target.getDeclaredAnnotation(Model.class);
                String value = anno.value();
                if(!StringUtils.isEmpty(prefix) && !value.substring(0,prefix.length()).equals(prefix)) {
                    InvocationHandler invocationHandler = Proxy.getInvocationHandler(anno);
                    Field values = invocationHandler.getClass().getDeclaredField("memberValues");
                    values.setAccessible(true);
                    Map memberValues = (Map) values.get(invocationHandler);
                    memberValues.put("value", Config.getTablePrefix() + "_" + value);
                }
                return anno;
            } else {
                throw new TractorException("@Model Not Found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 讲字符串加密成md5
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

    public static void main(String[] args) {
        // System.out.println(UnderlineToHump("user_name_and_password"));
        getModels();
    }

}
