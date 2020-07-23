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

/**
 * 根据数据库类型自动构建查询SQL并解析条件。
 *
 * @author lts
 * @email jiakesiws@gmail.com
 */
abstract class AbstractWrapper implements QueryCompare {

  private QueryCompare compare;

  @Override
  QueryCompare eq(column, object) {
    return compare.eq(column, object)
  }

  @Override
  QueryCompare ne(column, object) {
    return compare.ne(column, object)
  }

  @Override
  QueryCompare eqNull(column) {
    return compare.eqNull(column)
  }

  @Override
  QueryCompare neNull(column) {
    return compare.neNull(column)
  }

  @Override
  QueryCompare gt(column, object) {
    return compare.gt(column, object)
  }

  @Override
  QueryCompare ge(column, object) {
    return compare.ge(column, object)
  }

  @Override
  QueryCompare le(column, object) {
    return compare.le(column, object)
  }

  @Override
  QueryCompare lt(column, object) {
    return compare.lt(column, object)
  }

  @Override
  QueryCompare like(column, object) {
    return compare.like(column, object)
  }

  @Override
  QueryCompare da(column, int day) {
    return compare.da(column, day)
  }

  @Override
  QueryCompare dl(column, int day) {
    return compare.dl(column, day)
  }

  @Override
  QueryCompare between(column, a, b) {
    return compare.between(column, a, b)
  }

  /**
   * mysql sql脚本构建
   */
  class MySqlBuilder implements QueryCompare {

    @Override
    QueryCompare eq(column, object) {
      return this
    }

    @Override
    QueryCompare ne(column, object) {
      return this
    }

    @Override
    QueryCompare eqNull(column) {
      return this
    }

    @Override
    QueryCompare neNull(column) {
      return this
    }

    @Override
    QueryCompare gt(column, object) {
      return this
    }

    @Override
    QueryCompare ge(column, object) {
      return this
    }

    @Override
    QueryCompare le(column, object) {
      return this
    }

    @Override
    QueryCompare lt(column, object) {
      return this
    }

    @Override
    QueryCompare like(column, object) {
      return this
    }

    @Override
    QueryCompare da(column, int day) {
      return this
    }

    @Override
    QueryCompare dl(column, int day) {
      return this
    }

    @Override
    QueryCompare between(column, a, b) {
      return this
    }

  }

}
