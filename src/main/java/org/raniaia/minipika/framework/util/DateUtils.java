package org.raniaia.minipika.framework.util;

/*
 * Creates on 2019/12/7.
 */

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @author lts
 */
public final class DateUtils {

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

  public static final String TYPE_YYYYMMDD = "yyyyMMdd";
  public static final String TYPE_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
  public static final String TYPE_YYYY_MM_DD_HHSS = "yyyy-MM-dd HH:mm";
  public static final String TYPE_YYYY_MM_DD_HHSSMM = "yyyy-MM-dd HH:mm:ss";

  private DateTimeFormatter formatter;


  /**
   * private construct
   */
  private DateUtils() {
    this(TYPE_YYYY_MM_DD_HHSSMM);
  }

  /**
   * 初始化 {@link #formatter}
   *
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
   * current time for {@code Date}.
   */
  public static Date now() {
    return new Date();
  }

  /**
   * current time for {@code LocalDate}
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

  /**
   * 获取date的下n天的日期
   */
  public static Date getNextDay(Date date, int n) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DAY_OF_MONTH ,+n);
    date = calendar.getTime();
    return date;
  }

  /**
   * 获取date的下一天日期
   */
  public static Date getNextDay(Date date){
    return getNextDay(date,1);
  }

  /**
   * 获取明天的日期
   */
  public static Date getNextDay(){
    return getNextDay(now());
  }

  /**
   * 根据{@code LocalDate}来格式化
   * <p>
   * format date for {@code LocalDate}
   */
  public String format(LocalDateTime date) {
    return formatter.format(date);
  }

  /**
   * 根据{@code Date}来格式化
   * <p>
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

}
