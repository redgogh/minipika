package org.jiakesimk.minipika.components.mql

import org.jiakesimk.minipika.components.annotation.StructuredQuery
import org.jiakesimk.minipika.framework.common.ConstVariable

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
 * Creates on 2020/6/14.
 */

/**
 * 动态SQL构建
 *
 * @author tiansheng
 */
class MqlBuilder extends BaseBuilder {

  private Class<?> virtual

  MqlBuilder(Class<?> virtual) {
    super(ConstVariable.MQL_PROXY_CLASSNAME.concat(virtual.getSimpleName()))
    this.virtual = virtual
    initialization()
    end()
  }

  /**
   * 初始化
   */
  private void initialization() {
    def methods = virtual.methods
    methods.each({ method ->
      if (method.isAnnotationPresent(StructuredQuery)) {
        StructuredQuery query = method.getDeclaredAnnotation(StructuredQuery)
        String queryScript = query.value()
        createMethod(method, queryScript)
      }
    })
  }

}
