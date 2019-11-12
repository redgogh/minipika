package com.notfound.config;

import com.notfound.tools.StringUtils;

import java.io.InputStream;
import java.util.Properties;

/**
 * 读取配置
 *
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/4 14:10
 * @since 1.8
 */
public class Config {

    // properties对象
    private static Properties config;

    // 配置文件地址
    private static String configPath;

    // jdbc连接驱动
    private static String url = getValue("ormtools.jdbc.url");
    private static String driver = getValue("ormtools.jdbc.driver");

    // 数据库账号密码
    private static String username = getValue("ormtools.jdbc.username");
    private static String password = getValue("ormtools.jdbc.password");

    // 连接池配置
    private static String maxSize = getValue("ormtools.connectionPool.maxSize");
    private static String minSize = getValue("ormtools.connectionPool.minSize");

    // 数据库表名前缀
    private static String tablePrefix = getValue("ormtools.table.prefix");

    private static String getValue(String v) {
        try {
            if (config == null) {
                // 如果configPath等于空
                if (StringUtils.isEmpty(configPath)) {
                    configPath = "ormtools.properties";
                }
                InputStream in = Config.class.getClassLoader().getResourceAsStream(configPath);
                config = new Properties();
                config.load(in);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return config.getProperty(v);
    }

    public static String getUrl() {
        return url;
    }

    public static String getDriver() {
        return driver;
    }

    public static String getTablePrefix() {
        return tablePrefix;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public static Integer getMaxSize() {
        return Integer.valueOf(StringUtils.isEmpty(maxSize) ? "6" : maxSize);
    }

    public static Integer getMinSize() {
        return Integer.valueOf(StringUtils.isEmpty(minSize) ? "2" : minSize);
    }
}
