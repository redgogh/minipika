package org.jiakesimk.minipika.components.mql.groovy

import org.jiakesimk.minipika.components.annotation.Delete
import org.jiakesimk.minipika.components.annotation.Insert
import org.jiakesimk.minipika.components.annotation.Select
import org.jiakesimk.minipika.components.annotation.Update
import org.jiakesimk.minipika.framework.util.StringUtils

import java.lang.reflect.InvocationHandler
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
class MqlProxy implements InvocationHandler {

  private Class<?> virtual

  private Object agent

  /**
   * sql语句中的关键字集合
   */
  def keywords = ["nul": -1, "if": 0, "foreach": 1, "end": 2]

  MqlProxy(virtual) {
    this.virtual = virtual
    initialization()
  }

  /**
   * 构建Java代码并创建出一个代理类
   */
  private void initialization() {
    Method[] methods = virtual.getDeclaredMethods()
    methods.each { method ->
      // 如果存在Select注解
      if (method.isAnnotationPresent(Select.class)) {
        def select = method.getAnnotation(Select.class)
        def sql = select.value()
        syntaxAnalysis(sql, method)
      }
      // 如果存在Update注解
      if (method.isAnnotationPresent(Update.class)) {

      }
      // 如果存在Insert注解
      if (method.isAnnotationPresent(Insert.class)) {

      }
      // 如果存在Delete注解
      if (method.isAnnotationPresent(Delete.class)) {

      }
    }
  }

  /**
   * sql语法解析，将sql字符串解析成Java代码
   * @param sql sql字符串
   * @return Java方法对象
   */
  @SuppressWarnings("ALL")
  private static syntaxAnalysis(sql, Method method) {
    println method.parameters
    // 初始代码
    def code = new StringBuilder("""
      public String $method.name() { \n")
      code.append("StringBuilder sql = new StringBuilder();\n
    """)
    def lines = sql.split "\n"
    for (String line : lines) {
      line = line.trim()
      if (StringUtils.isEmpty(line)) {
        continue
      }
      code.append("\n")
      // 如果存在if标签的话就认定这是一个if语句
      if (line.contains("#if")) {
        line = line.replaceAll("#if", "if(")
        line = line.concat("){")
        code.append(line)
        continue
      }
      // 如果存在end标签的话就认定这是一个结束语句
      if (line.contains("#end")) {
        code.append("}")
        continue
      }
      code.append("sql.append(\"" + line + "\");")
    }
    code.append("return sql.toString();\n")
    code.append("}")
    return code.toString()
  }

  @Override
  Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    return null
  }

}
