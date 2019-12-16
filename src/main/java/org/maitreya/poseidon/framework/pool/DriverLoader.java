package org.maitreya.poseidon.framework.pool;

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
