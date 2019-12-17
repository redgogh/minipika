package org.laniakeamly.poseidon.model;

import java.net.MalformedURLException;

/**
 * @author 2BKeyboard
 * @version 1.0.0
 * @date 2019/11/21 19:36
 * @since 1.8
 */
public class SystemExmple {

    public static void main(String[] args) throws MalformedURLException {
        // System.setProperty("jdbc.drivers","com.mysql.cj.jdbc.Driver");
        // Enumeration<Driver> drivers = DriverManager.getDrivers();
        long startTime = System.currentTimeMillis();

        // StringUtils.format() 五百万次 7175ms
        // StringUtils.format() 一亿次 125727ms

        // StringUtils.format() 五百万次 1602
        // StringUtils.format() 一亿次 22547ms

        /*List list = new ArrayList<>();
        list.add("1");
        list.add("2");
        System.out.println(JSON.toJSONString(list));*/

        /*String sql = "select * from user_mod;el;";
        System.out.println(sql.length() +","+sql.lastIndexOf(";"));
        StringBuilder builder = new StringBuilder(sql);
        int selectPos = sql.indexOf("select") + "select".length();
        int fromPos = sql.indexOf("from");
        builder.replace(selectPos,fromPos," count(*) ");

        System.out.println(selectPos);
        System.out.println(fromPos);
        System.out.println(builder.toString());*/

        // System.out.println("`aaa`".replace("`",""));

        // System.out.println(Config.getInstance().getRefresh());
        // System.out.println(1 + 1 * 2 - 1);



        long endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime));
    }

}
