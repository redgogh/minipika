package org.laniakeamly.poseidon.framework.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Create by 2BKeyboard on 2019/12/7 0:54
 */
public class TimeUtils {

    /**
     * 一秒
     */
    public static final long SECOND = 1000;
    public static final String SECOND_STR = "second";

    /**
     * 一分钟
     */
    public static final long MINUTE = SECOND * 60;
    public static final String MINUTE_STR = "minute";

    /**
     * 一小时
     */
    public static final long HOUR = MINUTE * 60;
    public static final String HOUR_STR = "hour";

    /**
     * 一天
     */
    public static final long DAY = HOUR * 24;
    public static final String DAY_STR = "day";

    /**
     * 一周
     */
    public static final long WEEK = DAY * 7;
    public static final String WEEK_STR = "week";

    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        sdf.format(new Date());
    }

}
