package org.laniakeamly.poseidon.framework.tools;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Copyright: Create by TianSheng on 2019/12/7 0:54
 */
public final class DateUtils {

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

    private DateTimeFormatter formatter;

    public static final String TYPE_YYYY_MM_DD_HHSSMM = "yyyy-MM-dd HH:ss:mm";

    /**
     * private construct
     */
    private DateUtils() {
        this(TYPE_YYYY_MM_DD_HHSSMM);
    }

    /**
     * initialization {@link #formatter}
     * @param pattern the pattern
     */
    private DateUtils(String pattern) {
        formatter = DateTimeFormatter.ofPattern(pattern);
    }

    /**
     * Get default {@code DateUtils} instance.
     */
    public static DateUtils getInstance() {
        return new DateUtils();
    }

    /**
     * Get {@code DateUtils} instance by pattern.
     */
    public static DateUtils getInstance(String pattern) {
        return new DateUtils(pattern);
    }

    /**
     * 根据{@code LocalDate}来格式化
     *
     * format date for {@code LocalDate}
     */
    public String format(LocalDateTime date) {
        return formatter.format(date);
    }

    /**
     * 根据{@code Date}来格式化
     *
     * format date for {@code Date}
     */
    public String format(Date date) {
        return format(toLocalDateTime(date));
    }

    /**
     * 日期格式化 | date format
     *
     * @param pattern 格式化类型 | the pattern
     * @param date    Date对象 | Date object
     * @return 格式化后的String对象 | Date to String
     */
    public String format(String pattern, Date date) {
        return DateTimeFormatter.ofPattern(pattern).format(toLocalDateTime(date));
    }

    /**
     * 获取现在的格式化后的时间字符
     */
    public String formatNow() {
        return format(LocalDateTime.now());
    }

    /**
     * current time for {@code Date}.
     * @return
     */
    public static Date now() {
        return new Date();
    }

    /**
     * current time for {@code LocalDate}
     * @return
     */
    public static LocalDateTime localNow() {
        return LocalDateTime.now();
    }

    /**
     * 对象转{@code LocalDate} | Object to {@code LocalDate}
     *
     * @param date Date Object.
     * @return the LocalDate
     */
    public static LocalDateTime toLocalDateTime(Object date) {
        Instant instant = ((Date) date).toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }

}
