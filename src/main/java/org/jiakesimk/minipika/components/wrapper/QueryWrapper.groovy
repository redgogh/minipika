package org.jiakesimk.minipika.components.wrapper;

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
 * Creates on 2020/7/21.
 */

/**
 * 查询封装
 *
 * @author lts* @email ltsloveyellow@aliyun.com
 */
class QueryWrapper<T> extends AbstractWrapper<QueryWrapper<T>> {

  @Override
  QueryCompare<QueryWrapper<T>> eq(column, object) {
    return this
  }

  @Override
  QueryCompare<QueryWrapper<T>> ne(column, object) {
    return this
  }

  @Override
  QueryCompare<QueryWrapper<T>> eqNull(column) {
    return this
  }

  @Override
  QueryCompare<QueryWrapper<T>> neNull(column) {
    return this
  }

  @Override
  QueryCompare<QueryWrapper<T>> gt(column, object) {
    return this
  }

  @Override
  QueryCompare<QueryWrapper<T>> ge(column, object) {
    return this
  }

  @Override
  QueryCompare<QueryWrapper<T>> le(column, object) {
    return this
  }

  @Override
  QueryCompare<QueryWrapper<T>> lt(column, object) {
    return this
  }

  @Override
  QueryCompare<QueryWrapper<T>> like(column, object) {
    return this
  }

  @Override
  QueryCompare<QueryWrapper<T>> da(column, int day) {
    return this
  }

  @Override
  QueryCompare<QueryWrapper<T>> dl(column, int day) {
    return this
  }

  @Override
  QueryCompare<QueryWrapper<T>> between(column, a, b) {
    return this
  }

}
