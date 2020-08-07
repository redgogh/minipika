package org.jiakesiws.minipika.components.config;

/* ************************************************************************
 *
 * Copyright (C) 2020 2B键盘 All rights reserved.
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
 *
 * ************************************************************************/

/*
 * Creates on 2020/2/13.
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;


import org.jiakesiws.minipika.components.jdbc.datasource.unpooled.Dsi;
import org.jiakesiws.minipika.framework.config.Cfg;
import org.jiakesiws.minipika.framework.exception.ConfigException;
import org.jiakesiws.minipika.framework.jap.JapLoader;
import org.jiakesiws.minipika.framework.tools.Calculator;
import org.jiakesiws.minipika.framework.tools.Files;
import org.jiakesiws.minipika.framework.tools.StringUtils;
import org.jiakesiws.minipika.framework.tools.DateUtils;

import java.util.Map;
import java.util.Properties;

/**
 * @author 2B键盘
 * @since 1.8
 */
public abstract
class AbstractConfig {

    @Getter
    protected Dsi dsi;

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
    protected String transaction;
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
        this(new JapLoader().load().get("minipika"));
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

    public AbstractConfig(Cfg cfg){
        this((Object) cfg);
    }

    public AbstractConfig(Object config) {
        this.configObject = config;
        initConfig();
    }

    /**
     * 初始化
     */
    private void initConfig() {
        this.cache = getValue("cache.enable");
        this.refresh = getValue("cache.refresh");
        this.maxSize = getValue("pool.maximum");
        this.minSize = getValue("pool.minimum");
        this.tablePrefix = getValue("entity.prefix");
        this.transaction = getValue("jdbc.transaction");
        if (StringUtils.isEmpty(transaction)) {
            this.transaction = "false";
        }
        this.dsi = new Dsi(
                getValue("jdbc.url"),
                getValue("jdbc.driver"),
                getValue("jdbc.password"),
                getValue("jdbc.username"),
                Boolean.parseBoolean(this.transaction)
        );

        // 模型文件存放目录
        String e_pacakges = getValue("entity.package");
        if (!StringUtils.isEmpty(e_pacakges)) {
            this.entityPackage = e_pacakges.split(",");
        }

        // mapper映射文件存放位置
        String m_packlage = getValue("mapper.package");
        if (!StringUtils.isEmpty(m_packlage)) {
            this.mapperPackage = m_packlage.split(",");
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

        String temp = dsi.getUrl();
        for (int i = 0; i < 3; i++) {
            temp = temp.substring(temp.indexOf("/") + 1);
        }
        dbname = temp.substring(0, temp.indexOf("?"));

    }

    public String getValue(String key) {
        if (configObject instanceof Map) {
            return (String) ((Map) configObject).get(key);
        } else if (configObject instanceof Properties) {
            return ((Properties) configObject).getProperty("minipika.".concat(key));
        } else if(configObject instanceof  Cfg){
            int index = key.indexOf(".");
            String root = key.substring(0,index);
            return ((Cfg) configObject).get(root,key.substring(index+1));
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

    public Boolean gettransaction() {
        return Boolean.valueOf(transaction);
    }

    public boolean getCache() {
        return Boolean.parseBoolean(cache == null ? "false" : cache);
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
