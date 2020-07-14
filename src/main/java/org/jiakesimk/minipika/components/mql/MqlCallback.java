package org.jiakesimk.minipika.components.mql;

import javassist.NotFoundException;
import org.jiakesimk.minipika.components.annotation.Batch;
import org.jiakesimk.minipika.components.annotation.Insert;
import org.jiakesimk.minipika.components.annotation.Select;
import org.jiakesimk.minipika.components.annotation.Update;
import org.jiakesimk.minipika.components.jdbc.Executor;
import org.jiakesimk.minipika.components.logging.Log;
import org.jiakesimk.minipika.components.logging.LogFactory;
import org.jiakesimk.minipika.framework.annotations.Inject;
import org.jiakesimk.minipika.framework.exception.MinipikaException;
import org.jiakesimk.minipika.framework.util.ArrayUtils;
import org.jiakesimk.minipika.framework.util.Lists;

import javax.lang.model.type.NullType;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.SQLException;

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
@SuppressWarnings("unchecked")
public class MqlCallback<T> extends BaseBuilder implements InvocationHandler {

  private final Class<?> virtual;

  @Inject
  private Executor executor;

  private static final Log LOG = LogFactory.getLog(MqlCallback.class);

  private static final String MQL_PROXY_CLASSNAME = "org.minipika.components.proxy.$";

  public MqlCallback(Class<T> virtual) {
    super(MQL_PROXY_CLASSNAME.concat(virtual.getSimpleName()));
    this.virtual = virtual;
    try {
      initialization();
    } catch (NotFoundException e) {
      throw new RuntimeException(e);
    }
    buildEnd();
  }

  /**
   * 初始化
   */
  private void initialization() throws NotFoundException {
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
      if (method.isAnnotationPresent(Select.class)) {
        Select select = method.getDeclaredAnnotation(Select.class);
        createMethod(method, select.value());
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
      Object[] arguments = ArrayUtils.toArray(objects[1]);
      if (method.isAnnotationPresent(Select.class)) {
        Select select = method.getDeclaredAnnotation(Select.class);
        Class<?> returnType = select.forObject();
        if (returnType != NullType.class) {
          return executor.queryForObject(sql, returnType, arguments);
        }
        returnType = select.forList();
        if (returnType != NullType.class) {
          return executor.queryForList(sql, returnType, arguments);
        }
        // todo 查询模式实现
        String error = "Error executed query method failure. Cause: unrecognized return type.";
        LOG.error(error);
        throw new SQLException(error);
      }
      if (method.isAnnotationPresent(Update.class)
              || method.isAnnotationPresent(Insert.class)) {
        return executor.update(sql, arguments);
      }
      if (method.isAnnotationPresent(Batch.class)) {
        return executor.batch(sql, arguments);
      }
    } catch (Throwable e) {
      throw new MinipikaException(e.getMessage(), e);
    }
    return null;
  }

}
