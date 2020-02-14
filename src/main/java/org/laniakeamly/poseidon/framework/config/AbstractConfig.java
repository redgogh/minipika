package org.laniakeamly.poseidon.framework.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.laniakeamly.poseidon.framework.exception.runtime.ConfigException;
import org.laniakeamly.poseidon.framework.jap.JapLoader;
import org.laniakeamly.poseidon.framework.tools.Calculator;
import org.laniakeamly.poseidon.framework.tools.PIOUtils;
import org.laniakeamly.poseidon.framework.tools.StringUtils;
import org.laniakeamly.poseidon.framework.tools.TimeUtils;

import java.util.Map;
import java.util.Properties;

/**
 * Copyright by TianSheng on 2020/2/13 2:44
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 */
public abstract
class AbstractConfig implements PoseidonConfig {

    // jdbc连接驱动
    protected String url;
    // 数据库账号密码
    protected String username;
    protected String password;
    // 连接池配置
    protected String maxSize;
    protected String minSize;
    // 数据库表名前缀
    protected String tablePrefix;
    // model包路径
    protected String[] modelPackage;
    // mapper模板文件存放位置
    protected String[] mapperPackage;
    // 是否开启事物
    protected String transaction;
    // 是否开启缓存
    protected String cache;
    // 缓存过期时间
    protected String refresh;
    // 数据库名
    protected String dbname;
    // regular.json文件
    protected JSONObject regularJson;
    // default_model.json文件
    protected JSONObject defaultModel;

    private Object configObject;

    public AbstractConfig() {
        this(new JapLoader().load().get("poseidon"));
    }

    /**
     * 通过Jap配置文件加载
     * @param config
     */
    public AbstractConfig(Map<String, String> config) {
        this(((Object) config));
    }

    /**
     * 通过properties文件加载
     * @param config
     */
    public AbstractConfig(Properties config) {
        this(((Object) config));
    }

    public AbstractConfig(Object config) {
        this.configObject = config;
        initConfig();
    }

    /**
     * 初始化
     */
    private void initConfig() {
        this.url = getValue("jdbc.url");
        this.cache = getValue("jdbc.cache");
        this.refresh = getValue("jdbc.refresh");
        this.maxSize = getValue("connectionPool.maxSize");
        this.minSize = getValue("connectionPool.minSize");
        String driver = getValue("jdbc.driver");
        this.password = getValue("jdbc.password");
        this.username = getValue("jdbc.username");
        this.tablePrefix = getValue("model.prefix");
        this.transaction = getValue("jdbc.transaction");

        // 模型文件存放目录
        JSONArray modelArray = JSONArray.parseArray(getValue("model.package"));
        modelPackage = new String[modelArray.size()];
        modelArray.toArray(modelPackage);

        JSONArray mapperArray = JSONArray.parseArray(getValue("model.mapper"));
        mapperPackage = new String[modelArray.size()];
        mapperArray.toArray(mapperPackage);

        // 获取字段约束配置文件路径
        String regularJsonName = getValue("regular");
        if (StringUtils.isEmpty(regularJsonName)) regularJsonName = "regular.json";
        this.regularJson = PIOUtils.getResourceAsJson(regularJsonName);

        // 获取默认数据配置文件路径
        String defaultModelName = getValue("default.model");
        if (StringUtils.isEmpty(defaultModelName)) defaultModelName = "default_model.json";
        this.defaultModel = PIOUtils.getResourceAsJson(defaultModelName);

        loadDriver(driver);

        String temp = url;
        for (int i = 0; i < 3; i++) {
            temp = temp.substring(temp.indexOf("/") + 1);
        }
        dbname = temp.substring(0, temp.indexOf("?"));
        if (StringUtils.isEmpty(transaction)) transaction = "false";

    }

    @Override
    public String getValue(String key) {
        if (configObject instanceof Map) {
            return (String) ((Map) configObject).get(key);
        } else if (configObject instanceof Properties) {
            return ((Properties) configObject).getProperty("poseidon.".concat(key));
        }
        throw new ConfigException("unknown config object.");
    }

    @Override
    public void loadDriver(String classpath) {
        if (StringUtils.isEmpty(classpath)) {
            throw new ConfigException("jdbc driver cannot null.");
        }
        System.setProperty("jdbc.drivers", classpath);
    }

    public String getDbname() {
        return dbname;
    }

    public String[] getModelPackage() {
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

    public String[] getMapperBasePackage() {
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

    public JSONObject getDefaultModel(){
        return this.defaultModel;
    }

}
