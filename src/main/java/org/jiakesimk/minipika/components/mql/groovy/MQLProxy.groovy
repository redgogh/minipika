package org.jiakesimk.minipika.components.mql.groovy

import org.jiakesimk.minipika.components.annotation.Delete
import org.jiakesimk.minipika.components.annotation.Insert
import org.jiakesimk.minipika.components.annotation.Select
import org.jiakesimk.minipika.components.annotation.Update
import org.jiakesimk.minipika.components.mql.BaseBuilder
import org.jiakesimk.minipika.framework.compiler.JavaCompiler
import org.jiakesimk.minipika.framework.util.ClassUtils

import java.lang.reflect.Method

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
 * @author tiansheng
 */
class MQLProxy extends BaseBuilder {

  private Object agent

  private Class<?> virtual

  MQLProxy(virtual) {
    super(virtual.getSimpleName())
    this.virtual = virtual
    initialization()
    carryout()
    // 编译代码
    compileCodeInitAgent()
  }

  /**
   * 初始化
   */
  private void initialization() {
    Method[] methods = virtual.getDeclaredMethods()
    methods.each { method ->
      // 如果存在CRUD注解
      if (method.isAnnotationPresent(Select.class)
              || method.isAnnotationPresent(Update.class)
              || method.isAnnotationPresent(Insert.class)
              || method.isAnnotationPresent(Delete.class)) {
        createMethod(method)
      }
    }
  }

  /**
   * 编译代码
   */
  void compileCodeInitAgent() {
    Class<?> agent = JavaCompiler.compile(fullname, precompile as String)
    this.agent = ClassUtils.newInstance(agent);
  }

}
