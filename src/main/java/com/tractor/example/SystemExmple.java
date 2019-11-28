package com.tractor.example;

import java.math.BigDecimal;

/**
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/21 19:36
 * @since 1.8
 */
public class SystemExmple {

    public static void main(String[] args) {
        // System.setProperty("jdbc.drivers","com.mysql.cj.jdbc.Driver");
        // Enumeration<Driver> drivers = DriverManager.getDrivers();

        for (int i = 0; i < 10; i++) {
            switch (String.valueOf(i)) {
                case "1":
                case "2":
                case "3":{
                    System.out.println("1,2,3");
                    break;
                }
                case "4":
                case "5":
                case "6":{
                    System.out.println("4,5,6");
                    break;
                }
                case "7":
                case "8":
                case "9":{
                    System.out.println("7,8,9");
                    break;
                }
            }
        }

    }

}
