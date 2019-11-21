package com.tengu.pool;

import com.tengu.config.Config;

import java.sql.Driver;

/**
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/21 10:33
 * @since 1.8
 */
public class DriverLoader {

    private Driver driver;

    public DriverLoader() {
        try {
            Class<?> driver = ClassLoader.getSystemClassLoader().loadClass(Config.getDriver());
            this.driver = (Driver) driver.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Driver getDriver() {
        return driver;
    }
}
