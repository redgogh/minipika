package org.raniaia.poseidon.components.pool.connection;

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
