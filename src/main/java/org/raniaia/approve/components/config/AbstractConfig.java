package org.raniaia.approve.components.config;

/*
 * Copyright (C) 2020 Tiansheng All rights reserved.
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
 */

/*
 * Creates on 2020/2/13.
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import org.raniaia.available.io.Paths;
import org.raniaia.available.io.file.Files;
import org.raniaia.approve.components.jdbc.datasource.unpooled.IDataSource;
import org.raniaia.approve.framework.exception.ConfigException;
import org.raniaia.approve.framework.jap.JapLoader;
import org.raniaia.approve.framework.tools.Calculator;
import org.raniaia.approve.framework.tools.StringUtils;
import org.raniaia.approve.framework.tools.DateUtils;

import java.util.Map;
import java.util.Properties;

/**
 * @author tiansheng
 * @since 1.8
 */
public abstract
class AbstractConfig {

    @Getter
    protected IDataSource iDataSource;

    // 连接池配置
    protected String maxSize;
    protected String minSize;
    // 数据库表名前缀
    protected String tablePrefix;
    // entity包路径
    protected String[] entityPackage;
    // mapper模板文件存放位置
    protected String[] mapperPackage;
    // 是否开启事物
    protected String desiredAutoCommit;
    // 是否开启缓存
    protected String cache;
    // 缓存过期时间
    protected String refresh;
    // 数据库名
    protected String dbname;
    // norm.json文件
    protected JSONObject normJson;
    // default_entity.json文件
    protected JSONObject defaultEntity;

    private Object configObject;

    public AbstractConfig() {
        this(new JapLoader().load().get("approve"));
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
        this.cache = getValue("jdbc.cache");
        this.refresh = getValue("jdbc.refresh");
        this.maxSize = getValue("connectionPool.maxSize");
        this.minSize = getValue("connectionPool.minSize");
        this.tablePrefix = getValue("entity.prefix");
        this.desiredAutoCommit = getValue("jdbc.desiredAutoCommit");
        if (StringUtils.isEmpty(desiredAutoCommit)) {
            this.desiredAutoCommit = "false";
        }
        this.iDataSource = new IDataSource(
                getValue("jdbc.url"),
                getValue("jdbc.driver"),
                getValue("jdbc.password"),
                getValue("jdbc.username"),
                Boolean.parseBoolean(this.desiredAutoCommit)
        );

        // 模型文件存放目录
        JSONArray entityArray = JSONArray.parseArray(getValue("entity.package"));
        if (entityArray != null) {
            entityPackage = new String[entityArray.size()];
            entityArray.toArray(entityPackage);
        }
        JSONArray mapperArray = JSONArray.parseArray(getValue("entity.mapper"));
        if (mapperArray != null) {
            mapperPackage = new String[entityArray.size()];
            mapperArray.toArray(mapperPackage);
        }
        // 获取字段约束配置文件路径
        String normJsonName = getValue("entity.norm");
        if (!StringUtils.isEmpty(normJsonName)) {
            this.normJson = JSONObject.parseObject(Files.read(normJsonName));
        }

        // 获取默认数据配置文件路径
        String defaultEntityName = getValue("default.entity");
        if (!StringUtils.isEmpty(defaultEntityName)) {
            this.defaultEntity = JSONObject.parseObject(Files.read(defaultEntityName));
        }

        String temp = iDataSource.getUrl();
        for (int i = 0; i < 3; i++) {
            temp = temp.substring(temp.indexOf("/") + 1);
        }
        dbname = temp.substring(0, temp.indexOf("?"));

    }

    public String getValue(String key) {
        if (configObject instanceof Map) {
            return (String) ((Map) configObject).get(key);
        } else if (configObject instanceof Properties) {
            return ((Properties) configObject).getProperty("approve.".concat(key));
        }
        throw new ConfigException("unknown config object.");
    }

    public String getDbname() {
        return dbname;
    }

    public String[] getEntityPackage() {
        return entityPackage;
    }

    public String getTablePrefix() {
        return tablePrefix == null ? "" : tablePrefix;
    }

    public Integer getMaxSize() {
        return Integer.valueOf(StringUtils.isEmpty(maxSize) ? "6" : maxSize);
    }

    public Integer getMinSize() {
        return Integer.valueOf(StringUtils.isEmpty(minSize) ? "2" : minSize);
    }

    public Boolean getdesiredAutoCommit() {
        return Boolean.valueOf(desiredAutoCommit);
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
    public String getNorm(String name) {
        return (String) this.normJson.get(name);
    }

    public JSONObject getDefaultEntity() {
        return this.defaultEntity;
    }

}
