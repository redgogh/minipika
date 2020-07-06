package org.jiakesimk.minipika.components.mql

import org.jiakesimk.minipika.components.annotation.Batch

import org.jiakesimk.minipika.components.annotation.Select
import org.jiakesimk.minipika.components.annotation.Update
import org.jiakesimk.minipika.components.jdbc.Executor
import org.jiakesimk.minipika.components.jdbc.SQLExecutor
import org.jiakesimk.minipika.framework.common.ConstVariable
import org.jiakesimk.minipika.framework.exception.MinipikaException
import org.jiakesimk.minipika.framework.factory.Factorys
import org.jiakesimk.minipika.framework.logging.Log
import org.jiakesimk.minipika.framework.logging.LogFactory
import org.jiakesimk.minipika.framework.util.Lists

import javax.lang.model.type.NullType
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.sql.SQLException

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
class MqlCallback extends BaseBuilder implements InvocationHandler {

  private Class<?> virtual

  private Executor executor = Factorys.forClass(SQLExecutor)

  private static final Log LOG = LogFactory.getLog(MqlCallback)

  MqlCallback(Class<?> virtual) {
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
      if (method.isAnnotationPresent(Update)) {
        Update update = method.getDeclaredAnnotation(Update)
        createMethod(method, update.value())
      }
      if (method.isAnnotationPresent(Select)) {
        Select select = method.getDeclaredAnnotation(Select)
        createMethod(method, select.value())
      }
      if (method.isAnnotationPresent(Batch)) {
        Batch batch = method.getDeclaredAnnotation(Batch)
        createMethod(method, batch.value())
      }
    })
  }

  @SuppressWarnings("GrUnnecessaryPublicModifier")
  public <T> T bind() {
    return Proxy.newProxyInstance(this.class.getClassLoader(),
            new Class[]{virtual}, this) as T
  }

  @Override
  Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    try {
      def objects = invoke(method.name, args)
      String sql = objects[0]
      Object[] arguments = objects[1]
      if (method.isAnnotationPresent(Select)) {
        Select select = method.getDeclaredAnnotation(Select)
        Class<?> returnType = select.forObject()
        if (returnType != NullType) {
          return executor.queryForObject(sql, returnType, arguments)
        }
        returnType = select.forList()
        if (returnType != NullType) {
          return executor.queryForList(sql, returnType, arguments)
        }
        def error = "Error executed query method failure. Cause: unrecognized return type."
        LOG.error(error)
        throw new SQLException(error)
      }
      if (method.isAnnotationPresent(Update)) {
      }
      if (method.isAnnotationPresent(Batch)) {
        return executor.batch(sql, Lists.asList(arguments))
      }
    } catch (Throwable e) {
      throw new MinipikaException(e.message, e)
    }
  }

}
