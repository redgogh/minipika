package org.jiakesimk.minipika.components.wrapper

/* ************************************************************************
 *
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
 *
 * ************************************************************************/

/*
 * Creates on 2020/7/22.
 */

/**
 * 比较条件接口，仅适用于SQL查询。实现类自己封装自己的
 * 查询sql。
 *
 * @author lts
 * @email ltsloveyellow@aliyun.com
 */
interface QueryCompare<T> {

  /**
   * 等于
   *
   * @param column 字段名称
   * @param object 比较参数
   * @return 当前比较器（this）
   */
  QueryCompare<T> eq(column, object)

  /**
   * 不等于
   *
   * @param column 字段名称
   * @param object 比较参数
   * @return 当前比较器（this）
   */
  QueryCompare<T> ne(column, object)

  /**
   * 等于空的
   *
   * @param column 字段名称
   * @return 当前比较器（this）
   */
  QueryCompare<T> eqNull(column)

  /**
   * 不等于空的
   *
   * @param column 字段名称

   */
  QueryCompare<T> neNull(column)

  /**
   * 大于
   *
   * @param column 字段名称
   * @param object 比较参数
   * @return 当前比较器（this）
   */
  QueryCompare<T> gt(column, object)

  /**
   * 大于等于
   *
   * @param column 字段名称
   * @param object 比较参数
   * @return 当前比较器（this）
   */
  QueryCompare<T> ge(column, object)

  /**
   * 小于
   *
   * @param column 字段名称
   * @param object 比较参数
   * @return 当前比较器（this）
   */
  QueryCompare<T> le(column, object)

  /**
   * 小于等于
   *
   * @param column 字段名称
   * @param object 比较参数
   * @return 当前比较器（this）
   */
  QueryCompare<T> lt(column, object)

  /**
   * 模糊查询
   *
   * @param column 字段名称 %字段 | 字段% | %字段%
   * @param object 比较参数
   * @return 当前比较器（this）
   */
  QueryCompare<T> like(column, object)

  /**
   * 几天前
   *
   * @param column 字段名称 %字段 | 字段% | %字段%
   * @param day 天数
   * @return 当前比较器（this）
   */
  QueryCompare<T> da(column, int day)

  /**
   * 几天后
   *
   * @param column 字段名称 %字段 | 字段% | %字段%
   * @param day 天数
   * @return 当前比较器（this）
   */
  QueryCompare<T> dl(column, int day)

  /**
   * a-b之间的数据
   * 
   * @param column 字段名
   * @param a a值
   * @param b b值
   * @return 当前比较器（this）
   */
  QueryCompare<T> between(column, a, b)

}
