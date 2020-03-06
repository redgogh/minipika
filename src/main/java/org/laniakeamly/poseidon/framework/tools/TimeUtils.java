package org.laniakeamly.poseidon.framework.tools;

import java.text.SimpleDateFormat;

/**
 * Copyright: Create by TianSheng on 2019/12/7 0:54
 */
public final class TimeUtils {

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

    public static final String TYPE_YYYY_MM_DD_HHSSMM = "yyyy-MM-dd HH:ss:mm";

    private SimpleDateFormat sdf = new SimpleDateFormat(TYPE_YYYY_MM_DD_HHSSMM);

}
