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
 * Creates on 2020/6/18.
 */

import org.jiakesiws.minipika.framework.common.Callback;

import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 2B键盘
 * @email jiakesiws@gmail.com
 */
public class Matches {

  /**
   * 正则表达式匹配字符串，并返回结果
   *
   * @param s 源字符串
   * @param r 正则表达式, 如果是Java的情况下请传入字符串, groovy则传入
   *          两个斜杠组成的正则表达式语句，例子匹配井号后面的字符：/#(.*)/
   * @return 匹配结果数组，如果没有匹配到结果则返回空数组(不是空对象)
   */
  public static String[] find(String s, String r) {
    return find(s, r, null);
  }

  /**
   * 正则表达式匹配字符串，并返回结果
   *
   * @param s       源字符串
   * @param r       正则表达式, 如果是Java的情况下请传入字符串, groovy则传入
   *                两个斜杠组成的正则表达式语句，例子匹配井号后面的字符：/#(.*)/
   * @param closure 闭包，处理字符串内容。如果匹配结果需要另外的处理的话则创建一个{@link Consumer}对象
   *                并实现{@link Consumer#accept}方法，进行处理
   * @return 匹配结果数组，如果没有匹配到结果则返回空数组(不是空对象)
   */
  public static String[] find(String s, String r, Callback<String> closure) {
    Pattern par = Pattern.compile(r);
    Matcher matcher = par.matcher(s);
    List<String> groups = Lists.newArrayList();
    while (matcher.find()) {
      String value = matcher.group(1);
      if (closure == null) {
        groups.add(value);
      } else {
        groups.add(closure.accept(value));
      }
    }
    return groups.toArray(new String[groups.size()]);
  }

  public static void main(String[] args) {
    String s = "aa #{name} xx";
    String[] a = find(s, "#\\{(.*?)}", o -> {
      o = o.concat("0");
      return o.concat("1");
    });
    for (String s1 : a) {
      System.out.println(s1);
    }
  }

}
