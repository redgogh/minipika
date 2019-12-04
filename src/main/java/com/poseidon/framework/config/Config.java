package com.poseidon.framework.config;

import com.poseidon.framework.exception.ReadException;
import com.poseidon.framework.tools.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Create by 2BKeyboard on 2019/11/4 14:10
 */
public final class Config {

    // properties对象
    private Properties config;

    // 配置文件地址
    private String configPath = "poseidon.properties";

    // jdbc连接驱动
    private String url;
    // private  String driver = getValue("poseidon.jdbc.driver");

    // 数据库账号密码
    private String username;
    private String password;

    // 连接池配置
    private String maxSize;
    private String minSize;

    // 数据库表名前缀
    private String tablePrefix;

    // model包路径
    private String modelPackage;

    // 是否开启事物
    private String transaction;

    // 数据库名
    private String dbname;

    private static Config instance;

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config("poseidon.properties");
        }
        return instance;
    }

    public Config(String configPath) {
        if (!StringUtils.isEmpty(configPath)) {
            this.configPath = configPath;
        }
        initConfig();
        instance = this;
    }

    /**
     * 初始化
     */
    public void initConfig() {
        try {

            this.url                = getValue("poseidon.jdbc.url");
            String driver           = getValue("poseidon.jdbc.driver");
            this.username           = getValue("poseidon.jdbc.username");
            this.password           = getValue("poseidon.jdbc.password");
            this.tablePrefix        = getValue("poseidon.model.prefix");
            this.modelPackage       = getValue("poseidon.model.package");
            this.transaction        = getValue("poseidon.jdbc.transaction");
            this.minSize            = getValue("poseidon.connectionPool.minSize");
            this.maxSize            = getValue("poseidon.connectionPool.maxSize");

            System.setProperty("jdbc.drivers", driver);
            String temp = url;
            for (int i = 0; i < 3; i++) {
                temp = temp.substring(temp.indexOf("/") + 1);
            }
            dbname = temp.substring(0, temp.indexOf("?"));
            if (StringUtils.isEmpty(transaction)) transaction = "false";
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private String getValue(String v) {
        if (config == null) {
            InputStream in = Config.class.getClassLoader().getResourceAsStream(configPath);
            configLoad(in);
        }
        return config.getProperty(v);
    }


    private void configLoad(InputStream input) {
        if (input == null)
            throw new ReadException("cannot get config because config file path not exist \"" + configPath + "\"");
        config = new Properties();
        try {
            config.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getDbname() {
        return dbname;
    }

    public String getModelPackage() {
        return modelPackage;
    }

    public String getUrl() {
        return url;
    }

    public String getTablePrefix() {
        return tablePrefix == null ? "" : tablePrefix;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Integer getMaxSize() {
        return Integer.valueOf(StringUtils.isEmpty(maxSize) ? "6" : maxSize);
    }

    public Integer getMinSize() {
        return Integer.valueOf(StringUtils.isEmpty(minSize) ? "2" : minSize);
    }

    public Boolean getTransaction() {
        return Boolean.valueOf(transaction);
    }

}
