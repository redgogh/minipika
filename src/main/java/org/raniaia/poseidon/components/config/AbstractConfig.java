package org.raniaia.poseidon.components.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import org.raniaia.poseidon.framework.exception.runtime.ConfigException;
import org.raniaia.poseidon.framework.jap.JapLoader;
import org.raniaia.poseidon.framework.tools.Calculator;
import org.raniaia.poseidon.framework.tools.PIOUtils;
import org.raniaia.poseidon.framework.tools.StringUtils;
import org.raniaia.poseidon.framework.tools.DateUtils;

import java.util.Map;
import java.util.Properties;

/**
 * Copyright by tiansheng on 2020/2/13 2:44
 *
 * @author tiansheng
 * @version 1.0.0
 * @since 1.8
 */
public abstract
class AbstractConfig implements PoseidonConfig {

    // url
    protected String url;
    // 数据库账号密码
    protected String username;
    protected String password;
    // 驱动类型
    @Getter
    protected DriverType driverType;
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
     *
     * @param config
     */
    public AbstractConfig(Map<String, String> config) {
        this(((Object) config));
    }

    /**
     * 通过properties文件加载
     *
     * @param config
     */
    public AbstractConfig(Properties config) {
        this((Object) config);
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
        if (modelArray != null) {
            modelPackage = new String[modelArray.size()];
            modelArray.toArray(modelPackage);
        }
        JSONArray mapperArray = JSONArray.parseArray(getValue("model.mapper"));
        if (mapperArray != null) {
            mapperPackage = new String[modelArray.size()];
            mapperArray.toArray(mapperPackage);
        }
        // 获取字段约束配置文件路径
        String regularJsonName = getValue("model.regular");
        if (!StringUtils.isEmpty(regularJsonName)) {
            this.regularJson = PIOUtils.getResourceAsJson(regularJsonName);
        }

        // 获取默认数据配置文件路径
        String defaultModelName = getValue("default.model");
        if (!StringUtils.isEmpty(defaultModelName)) {
            this.defaultModel = PIOUtils.getResourceAsJson(defaultModelName);
        }

        this.driverType = loadDriver(driver);

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
            return DateUtils.HOUR * 6;
        }
        Calculator calculator = new Calculator();
        if (refresh.contains(DateUtils.SECOND_STR) ||
                refresh.contains(DateUtils.MINUTE_STR)
                || refresh.contains(DateUtils.HOUR_STR)
                || refresh.contains(DateUtils.DAY_STR)
                || refresh.contains(DateUtils.WEEK_STR)) {
            refresh = refresh.toLowerCase();
            refresh = refresh.replaceAll(DateUtils.SECOND_STR, String.valueOf(DateUtils.SECOND));
            refresh = refresh.replaceAll(DateUtils.MINUTE_STR, String.valueOf(DateUtils.MINUTE));
            refresh = refresh.replaceAll(DateUtils.HOUR_STR, String.valueOf(DateUtils.HOUR));
            refresh = refresh.replaceAll(DateUtils.DAY_STR, String.valueOf(DateUtils.DAY));
            refresh = refresh.replaceAll(DateUtils.WEEK_STR, String.valueOf(DateUtils.WEEK));
            return calculator.express(refresh);
        } else {
            return calculator.express(refresh) * DateUtils.SECOND;
        }

    }

    public String[] getMapperBasePackage() {
        return mapperPackage;
    }

    /**
     * 获取正则表达式内容
     *
     * @param name
     * @return
     */
    public String getRegular(String name) {
        return (String) this.regularJson.get(name);
    }

    public JSONObject getDefaultModel() {
        return this.defaultModel;
    }

}
