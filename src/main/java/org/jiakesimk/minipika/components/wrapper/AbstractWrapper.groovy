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
 * Creates on 2020/7/21.
 */

import static org.jiakesimk.minipika.framework.utils.Lists.newArrayList

/**
 * 根据数据库类型自动构建查询SQL并解析条件。
 *
 * @author lts
 * @email ltsloveyellow@aliyun.com
 */
abstract class AbstractWrapper implements QueryCompare {

  @Override
  QueryCompare eq(Object column, Object object) {
    return null
  }

  @Override
  QueryCompare ne(Object column, Object object) {
    return null
  }

  @Override
  QueryCompare eqNull(Object column) {
    return null
  }

  @Override
  QueryCompare neNull(Object column) {
    return null
  }

  @Override
  QueryCompare gt(Object column, Object object) {
    return null
  }

  @Override
  QueryCompare ge(Object column, Object object) {
    return null
  }

  @Override
  QueryCompare le(Object column, Object object) {
    return null
  }

  @Override
  QueryCompare lt(Object column, Object object) {
    return null
  }

  @Override
  QueryCompare like(Object column, Object object) {
    return null
  }

  @Override
  QueryCompare da(Object column, int day) {
    return null
  }

  @Override
  QueryCompare dl(Object column, int day) {
    return null
  }

  @Override
  QueryCompare between(Object column, Object a, Object b) {
    return null
  }

}
