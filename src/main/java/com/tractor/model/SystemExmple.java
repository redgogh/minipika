package com.tractor.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 2BKeyboard
 * @version 1.0.0
 * @date 2019/11/21 19:36
 * @since 1.8
 */
public class SystemExmple {

    public static void main(String[] args) {
        // System.setProperty("jdbc.drivers","com.mysql.cj.jdbc.Driver");
        // Enumeration<Driver> drivers = DriverManager.getDrivers();
        long startTime = System.currentTimeMillis();

        Map<String,String> map = new HashMap<>();
        map.remove("key");

        long endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime));
    }

}
