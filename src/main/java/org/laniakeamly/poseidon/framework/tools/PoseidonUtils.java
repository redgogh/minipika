package org.laniakeamly.poseidon.framework.tools;

import org.laniakeamly.poseidon.framework.annotation.Model;
import org.laniakeamly.poseidon.framework.config.Config;
import org.laniakeamly.poseidon.framework.exception.PoseidonException;
import org.laniakeamly.poseidon.framework.model.SecurityManager;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.util.TablesNamesFinder;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.security.MessageDigest;
import java.util.*;

/**
 * @author 2BKeyboard
 * @version 1.0.0
 * @date 2019/11/12 0:10
 * @since 1.8
 */
public final class PoseidonUtils {

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
        String basePackage = Config.getInstance().getModelPackage();
        try {
            getModels(basePackage, models);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return models;
    }

    private static void getModels(String basePackage, List<Class<?>> models) throws ClassNotFoundException {
        //扫描编译好的所有类路径
        URL url = PoseidonUtils.class.getResource("/" + basePackage.replaceAll("\\.", "/"));
        if (url == null) return;
        //将url转换为文件类型
        File dir = new File(url.getFile());
        for (File file : dir.listFiles()) {
            //判断file是否为一个文件目录
            if (file.isDirectory()) {
                //如果是一个文件目录就递归再往下读取
                getModels(basePackage + "." + file.getName(), models);
            } else {
                String classes = basePackage + "." + file.getName();
                if ("class".equals(classes.substring(classes.lastIndexOf(".") + 1))) {
                    classes = classes.replace(".class", "");
                    models.add(Class.forName(classes));
                }
            }
        }
    }

    /**
     * 获取Pte文件的File对象
     * @return List
     */
    public static List<File> getMapperXMLs() {
        List<File> mappers = new LinkedList<>();
        String basePackage = Config.getInstance().getMapperBasePackage();
        getXMLs(basePackage, mappers);
        return mappers;
    }

    private static void getXMLs(String basePackage, List<File> files) {
        //扫描编译好的所有类路径
        URL url = PoseidonUtils.class.getResource("/" + basePackage.replaceAll("\\.", "/"));
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
                String prefix = Config.getInstance().getTablePrefix();
                Model anno = target.getDeclaredAnnotation(Model.class);
                String value = anno.value();
                if (!StringUtils.isEmpty(prefix)) {
                    if(value.length() <= prefix.length() || !value.substring(0, prefix.length()).equals(prefix)){
                        InvocationHandler invocationHandler = Proxy.getInvocationHandler(anno);
                        Field values = invocationHandler.getClass().getDeclaredField("memberValues");
                        values.setAccessible(true);
                        Map memberValues = (Map) values.get(invocationHandler);
                        memberValues.put("value", Config.getInstance().getTablePrefix() + "_" + value);
                    }
                }
                return anno;
            } else {
                throw new PoseidonException("@Model Not Found.");
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

    public static List<String> getSQLTables(String sql) {
        TablesNamesFinder finder = new TablesNamesFinder();
        Statement statement = null;
        try {
            statement = CCJSqlParserUtil.parse(sql);
        } catch (JSQLParserException e) {
            return new ArrayList<>();
            // e.printStackTrace();
        }
        List<String> tables = finder.getTableList(statement);
        for (int i = 0, len = tables.size(); i < len; i++) {
            tables.set(i, tables.get(i).replace("`", ""));
        }
        return tables;
    }

    public static void main(String[] args) {
        // System.out.println(UnderlineToHump("user_name_and_password"));
        getModels();
    }

}
