package org.jiakesimk.minipika.components.wrapper

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
 * Creates on 2020/7/20.
 */

/**
 * 比较接口
 *
 * @author 2B键盘
 * @email jiakesiws@gmail.com
 */
interface Compare {

  /**
   * 等于
   *
   * @param column 字段名
   * @param val 比较值
   * @return 当前比较器（this）
   */
  Compare eq(column, val)

  /**
   * 不等于
   *
   * @param column 字段名
   * @param val 比较值
   * @return 当前比较器（this）
   */
  Compare ne(column, val)

  /**
   * 或
   *
   * @param column 字段
   * @param val 值
   * @return 当前比较器（this）
   */
  Compare or(column, val)

  /**
   * 等于空
   *
   * @param column 字段名
   * @return 当前比较器（this）
   */
  Compare eqNull(column)

  /**
   * 不等于空
   *
   * @param column 字段名
   * @return 当前比较器（this）
   */
  Compare neNull(column)

  /**
   * 大于
   *
   * @param column 字段名
   * @param val 比较值
   * @return 当前比较器（this）
   */
  Compare gt(column, val)

  /**
   * 大于等于
   *
   * @param column 字段名
   * @param val 比较值
   * @return 当前比较器（this）
   */
  Compare ge(column, val)

  /**
   * 小于等于
   *
   * @param column 字段名
   * @param val 比较值
   * @return 当前比较器（this）
   */
  Compare le(column, val)

  /**
   * 小于
   *
   * @param column 字段名
   * @param val 比较值
   * @return 当前比较器（this）
   */
  Compare lt(column, val)

  /**
   * 模糊，可以使用 %字段 | 字段% | %字段%
   *
   * @param column 字段名
   * @param val 比较值
   * @return 当前比较器（this）
   */
  Compare like(column, val)

  /**
   * 当需要查询的数据在多少天前的时候使用此方法
   *
   * @param column 字段名
   * @param day 比较值
   * @return 当前比较器（this）
   */
  Compare da(column, int day)

  /**
   * 当需要查询的数据在多少天后的时候使用此方法
   *
   * @param column 字段名
   * @param day 比较值
   * @return 当前比较器（this）
   */
  Compare dl(column, int day)

  /**
   * 当需要查询字段在a-b之间的时候使用此方法，
   * a-b不限制字段类型
   *
   * @param column 字段名
   * @param a a值
   * @param b b值
   * @return 当前比较器（this）
   */
  Compare between(column, a, b)

}