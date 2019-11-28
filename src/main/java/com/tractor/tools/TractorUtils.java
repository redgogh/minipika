package com.tractor.tools;

import com.tractor.annotation.Model;
import com.tractor.config.Config;
import com.tractor.exception.TractorException;
import com.tractor.model.SecurityManager;

import java.io.File;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 404NotFoundx
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
        URL url = TractorUtils.class.getResource("/"+basePackage.replaceAll("\\.","/"));
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
     * @param target
     * @return
     */
    public static String getModelValue(Class<?> target){
        return getModelAnnotation(target).value();
    }

    /**
     * 获取Model注解
     * @param target
     * @return
     */
    public static Model getModelAnnotation(Class<?> target){
        try {
            if (SecurityManager.existModel(target)) {
                return target.getDeclaredAnnotation(Model.class);
            } else {
                throw new TractorException("@Model Not Found.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        // System.out.println(UnderlineToHump("user_name_and_password"));
        getModels();
    }

}
