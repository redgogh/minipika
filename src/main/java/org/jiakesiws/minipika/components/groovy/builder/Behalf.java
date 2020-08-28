package org.jiakesiws.minipika.components.groovy.builder;

import org.jiakesiws.minipika.components.annotation.Batch;
import org.jiakesiws.minipika.components.annotation.Insert;
import org.jiakesiws.minipika.components.annotation.QueryOf;
import org.jiakesiws.minipika.components.annotation.Update;
import org.jiakesiws.minipika.components.jdbc.Executor;
import org.jiakesiws.minipika.components.logging.Log;
import org.jiakesiws.minipika.components.logging.LogFactory;
import org.jiakesiws.minipika.framework.annotations.Component;
import org.jiakesiws.minipika.framework.common.ConstVariable;
import org.jiakesiws.minipika.framework.factory.ClassLoaders;
import org.jiakesiws.minipika.framework.utils.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

/* ************************************************************************
 *
 * Copyright (C) 2020 2B键盘 All rights reserved.
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
 * @author 2B键盘
 * @email jiakesiws@gmail.com
 */
@SuppressWarnings("unchecked")
public class Behalf<T> extends InvokeBuilder implements InvocationHandler
{

  private final Class<?> virtual;

  @Component
  private Executor executor;

  private final Map<String, Configuration> configurations = Maps.newConcurrentHashMap();

  private static final Log LOG = LogFactory.getLog(Behalf.class);

  private static final String MQL_PROXY_CLASSNAME = "org.jiakesiws.minipika.components.proxy.$";

  public Behalf(Class<T> virtual)
  {
    super(MQL_PROXY_CLASSNAME.concat(virtual.getSimpleName()));
    this.virtual = virtual;
    initialization();
    over();
  }

  /**
   * 初始化
   */
  private void initialization()
  {
    Method[] methods = virtual.getDeclaredMethods();
    for (Method method : methods)
    {
      if (method.isAnnotationPresent(ConstVariable.A_UPDATE))
      {
        Update update = (Update) method.getDeclaredAnnotation(ConstVariable.A_UPDATE);
        createMethod(method, update.value());
      }
      if (method.isAnnotationPresent(ConstVariable.A_INSERT))
      {
        Insert insert = (Insert) method.getDeclaredAnnotation(ConstVariable.A_INSERT);
        createMethod(method, insert.value());
      }
      if (method.isAnnotationPresent(ConstVariable.A_QUERY_OF))
      {
        QueryOf queryOf = (QueryOf) method.getDeclaredAnnotation(ConstVariable.A_QUERY_OF);
        createMethod(method, queryOf.value());
      }
      if (method.isAnnotationPresent(ConstVariable.A_INSERT))
      {
        Batch batch = (Batch) method.getDeclaredAnnotation(ConstVariable.A_INSERT);
        createMethod(method, batch.value());
      }
    }
  }

  public T bind()
  {
    return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(),
            new Class[]{virtual}, this);
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Exception
  {
    String name = method.getName();
    if (!"toString".equals(name))
    {
      Configuration configuration;
      Object[] objects = invoke(method, args);
      String key = UniqueId.genKey(name, method.getParameters());
      if (configurations.containsKey(key))
      {
        configuration = configurations.get(key);
      } else
      {
        configuration = ClassLoaders.forClass(Configuration.class,
                new Class[]{Method.class, Object.class}, method, instance);
        configuration.setConfigurationId(key);
        configurations.put(key, configuration);
      }
      return configuration.execute((String) objects[0], Arrays.toArray(objects[1]));
    } else
    {
      return getClass().getName() + "@" + Integer.toHexString(hashCode());
    }
  }

}
