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

        // String.format() 五百万次 2820ms
        // String.format() 一亿次 46683ms

        // StringUtils.format() 五百万次 1602
        // StringUtils.format() 一亿次 22547ms

        String v = "{}今天要去{}上学";
        for(int i=0; i<100000000; i++){
            String.format(v,"小明"+i,"怀远九义校"+i);
        }

        long endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime));
    }

}
