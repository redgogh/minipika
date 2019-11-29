package com.tractor.framework.pool;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

/**
 * @author 2BKeyboard
 * @version 1.0.0
 * @date 2019/11/21 10:33
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
                throw new NullPointerException("未获取驱动程序，请检查驱动路径是否有问题");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Driver getDriver() {
        return driver;
    }
}
