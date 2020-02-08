package org.laniakeamly.poseidon.framework.config;

import com.alibaba.fastjson.JSONObject;
import org.laniakeamly.poseidon.framework.exception.runtime.ReadException;
import org.laniakeamly.poseidon.framework.model.LoaderModel;
import org.laniakeamly.poseidon.framework.tools.*;

import java.io.InputStream;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 配置类
 *
 * Config object.
 *
 * Copyright: Create by TianSheng on 2019/11/4 14:10
 *
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 *
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

    // mapper模板文件存放位置
    private String mapperPackage;

    // 是否开启事物
    private String transaction;

    // 是否开启缓存
    private String cache;

    // 缓存过期时间
    private String refresh;

    // 数据库名
    private String dbname;

    // regular.json文件
    private JSONObject regularJson;

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
        // 当配置文件第一次加载时初始化
        LoaderModel init = new LoaderModel();
        init.run();
    }

    /**
     * 初始化
     */
    public void initConfig() {
        try {

            this.mapperPackage = getValue("poseidon.model.mapper");
            this.url = getValue("poseidon.jdbc.url");
            this.cache = getValue("poseidon.jdbc.cache");
            this.refresh = getValue("poseidon.jdbc.refresh");
            String driver = getValue("poseidon.jdbc.driver");
            this.username = getValue("poseidon.jdbc.username");
            this.password = getValue("poseidon.jdbc.password");
            this.tablePrefix = getValue("poseidon.model.prefix");
            this.modelPackage = getValue("poseidon.model.package");
            this.transaction = getValue("poseidon.jdbc.transaction");
            this.maxSize = getValue("poseidon.connectionPool.maxSize");
            this.minSize = getValue("poseidon.connectionPool.minSize");

            // 获取字段约束配置文件路径
            String regularJsonName = getValue("poseidon.regular.json");
            if (StringUtils.isEmpty(regularJsonName)) regularJsonName = "regular.json";
            this.regularJson = PIOUtils.getResourceAsJson(regularJsonName);


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

    private String getValue(String key) {
        if (config == null) {
            configLoad();
        }
        return config.getProperty(key);
    }

    private String[] getValueArray(String key){
        int index = 0;
        List<String> list = new ArrayList<>();
        while(true){
            String value = getValue(key.concat("[").concat(String.valueOf(index)).concat("]"));
            if(StringUtils.isEmpty(value)){
                break;
            }
            list.add(value);
        }
        return (String[]) list.toArray();
    }


    private void configLoad() {
        InputStream in = PIOUtils.getResourceAsStream(configPath);
        if (in == null)
            throw new ReadException("cannot get config because config file path not exist \"" + configPath + "\"");
        config = PIOUtils.getResourceAsProperties(in);
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

    public boolean getCache() {
        return Boolean.valueOf(cache == null ? "false" : cache);
    }

    public long getRefresh() {
        if (StringUtils.isEmpty(refresh)) {
            return TimeUtils.HOUR * 6;
        }
        Calculator calculator = new Calculator();
        if (refresh.contains(TimeUtils.SECOND_STR) ||
                refresh.contains(TimeUtils.MINUTE_STR)
                || refresh.contains(TimeUtils.HOUR_STR)
                || refresh.contains(TimeUtils.DAY_STR)
                || refresh.contains(TimeUtils.WEEK_STR)) {
            refresh = refresh.toLowerCase();
            refresh = refresh.replaceAll(TimeUtils.SECOND_STR, String.valueOf(TimeUtils.SECOND));
            refresh = refresh.replaceAll(TimeUtils.MINUTE_STR, String.valueOf(TimeUtils.MINUTE));
            refresh = refresh.replaceAll(TimeUtils.HOUR_STR, String.valueOf(TimeUtils.HOUR));
            refresh = refresh.replaceAll(TimeUtils.DAY_STR, String.valueOf(TimeUtils.DAY));
            refresh = refresh.replaceAll(TimeUtils.WEEK_STR, String.valueOf(TimeUtils.WEEK));
            return calculator.express(refresh);
        } else {
            return calculator.express(refresh) * TimeUtils.SECOND;
        }

    }

    public String getMapperBasePackage() {
        return mapperPackage;
    }

    /**
     * 获取正则表达式内容
     * @param name
     * @return
     */
    public String getRegular(String name) {
        return (String) this.regularJson.get(name);
    }

    public Pattern getPatternForRegularJson(String name) {
        return RegularUtils.getInstanceSave().getPattern(getRegular(name));
    }

}
