package org.jiakesiws.minipika.framework.util;

/*
 * Creates on 2020/5/18.
 */

import java.text.MessageFormat;
import java.util.UUID;

/**
 * 生成唯一id
 *
 * @author lts
 */
public class UniqueId {

  /**
   * 在原生的UUID中有五个不同的字符<code>6af63b35-9998-418e-94bf-53aca17c6ef0</code>
   * 这个方法将它们分割成了五个独立的字符串，pattern可以随意组装UUID的生成格式。
   *
   * {0}代表第一串字符，也就是：6af63b35
   * {1}代表第一串字符，也就是：9998
   * {2}代表第一串字符，也就是：418e
   * {3}代表第一串字符，也就是：94bf
   * {4}代表第一串字符，也就是：53aca17c6ef0
   *
   * 你可以根据这个格式随意组装UUID，比如：<code>
   *   getUUID("DB1{0}{1}{2}{5}2020");
   * </code>
   *
   * 结果：DB1A912569A52DD5880B52BE7F62020
   */
  public static String getUUID(String pattern) {
    Object[] uuid = getSourceUUID().split("-");
    String rs = MessageFormat.format(pattern, uuid);
    return StringUtils.toUpperCase(rs);
  }

  public static String getUUID() {
    String uuid = StringUtils.toUpperCase(getSourceUUID());
    uuid = uuid.replaceAll("-", "");
    return uuid;
  }

  public static String getSourceUUID(){
    return UUID.randomUUID().toString();
  }

}