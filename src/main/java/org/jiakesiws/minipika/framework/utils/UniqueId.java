package org.jiakesiws.minipika.framework.utils;

/* ************************************************************************
 *
 * Copyright (C) 2020 2B键盘 All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ************************************************************************/

/*
 * Creates on 2020/5/18.
 */

import java.text.MessageFormat;
import java.util.UUID;

/**
 * 生成唯一id
 *
 * @author lts
 * @email jiakesiws@gmail.com
 */
public class UniqueId {

  /**
   * 在原生的UUID中有五个不同的字符<code>6af63b35-9998-418e-94bf-53aca17c6ef0</code>
   * 这个方法将它们分割成了五个独立的字符串，pattern可以随意组装UUID的生成格式。
   * <p>
   * {0}代表第一串字符，也就是：6af63b35
   * {1}代表第一串字符，也就是：9998
   * {2}代表第一串字符，也就是：418e
   * {3}代表第一串字符，也就是：94bf
   * {4}代表第一串字符，也就是：53aca17c6ef0
   * <p>
   * 你可以根据这个格式随意组装UUID，比如：<code>
   * getUUID("DB1{0}{1}{2}{5}2020");
   * </code>
   * <p>
   * 结果：DB1A912569A52DD5880B52BE7F62020
   */
  public static String getUUID(String pattern) {
    Object[] uuid = getSourceUUID().split("-");
    String rs = MessageFormat.format(pattern, uuid);
    return StringUtils.toUpperCase(rs);
  }

  /**
   * @return 没有横线的UUID
   */
  public static String getUUID() {
    String uuid = StringUtils.toUpperCase(getSourceUUID());
    uuid = uuid.replaceAll("-", "");
    return uuid;
  }

  /**
   * @return 没有进行任何修改的UUID
   */
  public static String getSourceUUID() {
    return UUID.randomUUID().toString();
  }

  /**
   * 自己生成key值
   *
   * @param master 主要的字符串
   * @param e      其他元素
   * @return key
   */
  public static <E> String genKey(String master, E... e) {
    StringBuilder builder = new StringBuilder(master);
    for (Object o : e) {
      builder.append(o);
    }
    return CEA64.MD5.digest32(builder.toString());
  }

}