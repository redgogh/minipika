package org.jiakesimk.minipika.components.mql;

import org.jiakesimk.minipika.components.annotation.Batch;
import org.jiakesimk.minipika.components.annotation.Insert;
import org.jiakesimk.minipika.components.annotation.QueryOf;
import org.jiakesimk.minipika.components.annotation.Update;
import org.jiakesimk.minipika.components.jdbc.Executor;
import org.jiakesimk.minipika.components.logging.Log;
import org.jiakesimk.minipika.components.logging.LogFactory;
import org.jiakesimk.minipika.framework.annotations.Component;
import org.jiakesimk.minipika.framework.utils.Arrays;
import org.jiakesimk.minipika.framework.utils.Methods;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

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
 * Creates on 2020/6/14.
 */

/**
 * 动态SQL构建
 *
 * @author tiansheng
 * @email jiakesiws@gmail.com
 */
@SuppressWarnings("unchecked")
public class MqlCallback<T> extends BaseBuilder implements InvocationHandler {

  private final Class<?> virtual;

  @Component
  private Executor executor;

  private static final Log LOG = LogFactory.getLog(MqlCallback.class);

  private static final String MQL_PROXY_CLASSNAME = "org.minipika.components.proxy.$";

  public MqlCallback(Class<T> virtual) {
    super(MQL_PROXY_CLASSNAME.concat(virtual.getSimpleName()));
    this.virtual = virtual;
    initialization();
    buildEnd();
  }

  /**
   * 初始化
   */
  private void initialization() {
    Method[] methods = virtual.getDeclaredMethods();
    for (Method method : methods) {
      if (method.isAnnotationPresent(Update.class)) {
        Update update = method.getDeclaredAnnotation(Update.class);
        createMethod(method, update.value());
      }
      if (method.isAnnotationPresent(Insert.class)) {
        Insert insert = method.getDeclaredAnnotation(Insert.class);
        createMethod(method, insert.value());
      }
      if (method.isAnnotationPresent(QueryOf.class)) {
        QueryOf queryOf = method.getDeclaredAnnotation(QueryOf.class);
        createMethod(method, queryOf.value());
      }
      if (method.isAnnotationPresent(Batch.class)) {
        Batch batch = method.getDeclaredAnnotation(Batch.class);
        createMethod(method, batch.value());
      }
    }
  }

  public T bind() {
    return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(),
            new Class[]{virtual}, this);
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) {
    try {
      Object[] objects = invoke(method, args);
      String sql = (String) objects[0];
      Object[] arguments = Arrays.toArray(objects[1]);
      if (method.isAnnotationPresent(QueryOf.class)) {
        return doSelectQuery(method, sql, arguments);
      }
      if (method.isAnnotationPresent(Update.class)
              || method.isAnnotationPresent(Insert.class)) {
        return executor.update(sql, arguments);
      }
      if (method.isAnnotationPresent(Batch.class)) {
        return executor.batch(sql, arguments);
      }
    } catch (Throwable e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 做条件查询
   *
   * @param method    查询方法
   * @param sql       sql语句
   * @param arguments 参数列表
   * @return 返回对象
   */
  private Object doSelectQuery(Method method, String sql, Object[] arguments) throws Exception {
    Class<?> returnType = method.getReturnType();
    if (returnType == List.class) {
      String signature = Methods.getGenericSignature(method);
      signature = signature.substring(signature.lastIndexOf("<"))
      .replaceAll("<L","")
      .replaceAll(";>;","")
      .replaceAll("/","\\.");
      return executor.queryForList(sql, Class.forName(signature), arguments);
    }
    return executor.queryForObject(sql, returnType, arguments);
  }

}
