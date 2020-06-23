package org.jiakesimk.minipika.framework.util;

/*
 * Copyright (C) 2020 tiansheng All rights reserved.
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
 */

/*
 * Creates on 2020/6/18.
 */

/**
 * @author tiansheng
 */
class Matches {

  /**
   * 正则表达式匹配字符串，并返回结果
   *
   * @param s 源字符串
   *
   * @param r 正则表达式, 如果是Java的情况下请传入字符串, groovy则传入
   *          两个斜杠组成的正则表达式语句，例子匹配井号后面的字符：/#(.*)/
   * @param closure 闭包，处理字符串内容。如果匹配结果需要另外的处理的话则创建一个{@link Closure}对象
   *                并实现{@link Closure#call}方法，进行处理
   * @return 匹配结果数组，如果没有匹配到结果则返回空数组(不是空对象)
   */
  static String[] find(s, r, Closure closure = null) {
    def group = []
    def m = s =~ r
    while (m.find()) {
      def v = m.group(1)
      if (closure != null) {
        group.add closure.call(v)
      } else {
        group.add v
      }
    }
    return group
  }

}
