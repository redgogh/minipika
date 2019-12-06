package com.poseidon.framework.tools;

import com.poseidon.framework.exception.ExpressionException;

/**
 * Create by 2BKeyboard on 2019/12/7 0:54
 */
public class TimeUtils {

    /**
     * 一秒
     */
    public static final long SECOND = 1000;

    /**
     * 一分钟
     */
    public static final long MINUTE = SECOND * 60;

    /**
     * 一小时
     */
    public static final long HOUR = MINUTE * 60;

    /**
     * 一天
     */
    public static final long DAY = HOUR * 24;

    /**
     * 一周
     */
    public static final long WEEK = DAY * 7;

    public static long getTime(String value) {
        switch (value) {
            case "second": {
                return TimeUtils.SECOND;
            }
            case "minute": {
                return TimeUtils.MINUTE;
            }
            case "hour": {
                return TimeUtils.HOUR;
            }
            case "day": {
                return TimeUtils.DAY;

            }
            case "week": {
                return TimeUtils.WEEK;
            }
            default: {
                throw new ExpressionException("refresh expression error: " + value);
            }
        }
    }

}
