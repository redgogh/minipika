package org.raniaia.poseidon.components.pool.connection;

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
 * Creates on 2019/11/21.
 */

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

/**
 *
 * 由于JDBC在并发下有创建异常的情况，所以写了个加载类.
 *
 * Jdbc driver loader.
 *
 * @author tiansheng
 * @version 1.0.0
 * @since 1.8
 */
public class DriverLoader {

    private Driver driver;

    public DriverLoader() {
        try {
            Enumeration<Driver> enumeration = DriverManager.getDrivers();
            Driver driver = enumeration.nextElement();
            if(driver != null){
                this.driver = driver;
            }else{
                throw new NullPointerException("no jdbc driver");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Driver getDriver() {
        return driver;
    }
}
