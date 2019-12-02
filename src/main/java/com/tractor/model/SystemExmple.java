package com.tractor.model;

import com.tractor.framework.tools.StringUtils;

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

        // StringUtils.format() 五百万次 7175ms
        // StringUtils.format() 一亿次 125727ms

        // StringUtils.format() 五百万次 1602
        // StringUtils.format() 一亿次 22547ms

        System.out.println(StringUtils.format("show table status from {} where name = '{}';","user_model","aaa"));

        long endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime));
    }

}
