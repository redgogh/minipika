package org.raniaia.approve.components.jdbc.datasource.unpooled;

/*
 * Copyright (C) 2020 tiansheng All rights reserved.
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
 * Creates on 2020/3/25.
 */

import lombok.Getter;
import lombok.Setter;
import org.raniaia.available.map.Maps;
import org.raniaia.approve.framework.loader.NativeClassLoader;
import org.raniaia.approve.framework.tools.StringUtils;

import java.sql.Driver;
import java.util.Map;
import java.util.Properties;

/**
 * @author tiansheng
 */
public class IDataSource {

    // 存放驱动实例
    static Map<String, Driver> registerDrivers = Maps.newConcurrentHashMap();

    @Getter
    protected String url;
    @Getter
    protected String driver;
    @Getter
    protected String username;
    @Getter
    protected String password;

    /*
     * 标明自己应该被哪个DataSource所创建
     */
    @Getter
    @Setter
    protected String sourceType;

    ClassLoader driverClassLoader;

    // 是否设置为自动提交
    Boolean autoCommit;

    public IDataSource() {
    }

    public IDataSource(String url, String driver, String username,
                       String password) {
        this(url, driver, username, password, false, new NativeClassLoader());
    }

    public IDataSource(String url, String driver, String username,
                       String password,boolean autoCommit) {
        this(url, driver, username, password, autoCommit, new NativeClassLoader());
    }

    public IDataSource(String url, String driver, String username,
                       String password, Boolean autoCommit, ClassLoader classLoader) {
        this.url = url;
        this.driver = driver;
        this.username = username;
        this.password = password;
        this.autoCommit = autoCommit;
        this.driverClassLoader = classLoader;
    }

    static Properties buildDriverInfo(String username, String password) {
        final Properties info = new Properties();
        if (!StringUtils.isEmpty(username)) {
            info.setProperty("user", username);
        }
        if (!StringUtils.isEmpty(password)) {
            info.setProperty("password", password);
        }
        return info;
    }

}
