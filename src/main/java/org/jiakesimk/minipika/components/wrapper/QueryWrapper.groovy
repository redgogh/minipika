package org.jiakesimk.minipika.components.wrapper;

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
 * Creates on 2020/7/21.
 */

/**
 * 查询封装
 *
 * @author lts
 */
class QueryWrapper<T> extends AbstractWrapper<T> {

  QueryWrapper() {
  }

  /**
   * 设置查询字段
   *
   * @param column 字段列表
   */
  void select(String... column) {

  }

  /**
   * 等于
   *
   * @param column 字段名称
   * @param object 比较字段
   */
  void eq(column, object) {}

  /**
   * 不等于
   *
   * @param column 字段名称
   * @param object 比较字段
   */
  void ne(column, object) {}

  /**
   * 大于
   *
   * @param column 字段名称
   * @param object 比较字段
   */
  void gt(column, object) {}

  /**
   * 大于等于
   *
   * @param column 字段名称
   * @param object 比较字段
   */
  void ge(column, object) {}

  /**
   * 小于
   *
   * @param column 字段名称
   * @param object 比较字段
   */
  void le(column, object) {}

  /**
   * 小于等于
   *
   * @param column 字段名称
   * @param object 比较字段
   */
  void lt(column, object) {}

}
