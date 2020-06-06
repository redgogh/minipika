package org.raniaia.minipika.framework.factory;

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
 * Creates on 2020/6/1.
 */

import org.raniaia.minipika.components.jdbc.DefaultResultSet;
import org.raniaia.minipika.components.jdbc.QueryResultSet;
import org.raniaia.minipika.components.jdbc.transaction.DefaultTransaction;
import org.raniaia.minipika.components.jdbc.transaction.DefaultTransactionFactory;
import org.raniaia.minipika.components.jdbc.transaction.Transaction;
import org.raniaia.minipika.components.jdbc.transaction.TransactionFactory;
import org.raniaia.minipika.framework.util.ClassUtils;
import org.raniaia.minipika.framework.util.Maps;

import java.util.Map;

/**
 * @author tiansheng
 */
public class DefaultComponentFactory implements ComponentFactory {

  /**
   * 组件容器
   */
  private static final Map<String, Object> components = Maps.newConcurrentHashMap(8);

  private static ComponentFactory factory;

  private DefaultComponentFactory() {
    {
      components.put(QueryResultSet.class.getName(),     forClass(DefaultResultSet.class));
      components.put(DefaultTransaction.class.getName(), forClass(DefaultTransaction.class));
      components.put(TransactionFactory.class.getName(), forClass(DefaultTransactionFactory.class));
    }
  }

  public static ComponentFactory getFactory() {
    if (factory == null) {
      factory = new DefaultComponentFactory();
    }
    return factory;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T forClass(Class<?> clazz) {
    return forClass(clazz, null);
  }

  @Override
  public <T> T forClass(Class<?> clazz, Class<?>[] types, Object... parameter) {
    Object component = null;
    String className = clazz.getName();
    component = components.get(className);
    if (!component.equals(null)) {
      return (T) component;
    }
    try {
      if (ClassUtils.isInterface(clazz)) {
        // 如果是接口的话那么就查找匹配接口类型的类
        component = findMatchesClassType(clazz);
        if (component != null) {
          return (T) component;
        }
      }
      // 判断是否执行有参构造器
      if (types == null) {
        component = InjectUtils.minipika(clazz, components);
      }
      component = InjectUtils.minipika(clazz, types, components, parameter);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    if (component != null) {
      components.put(className, component);
    }
    return (T) component;
  }

  /**
   * 查找接口匹配的实现类
   *
   * @param IFACE 接口对象
   * @return 对应的实现类
   */
  private static Object findMatchesClassType(Class<?> IFACE) {
    for (Object value : components.values()) {
      Class<?>[] interfaces = value.getClass().getInterfaces();
      if (interfaces != null && interfaces.length != 0) {
        for (Class<?> IFACES : interfaces) {
          if (IFACE == IFACES) {
            return value;
          }
        }
      }
    }
    return null;
  }

  @Override
  public void reloading(String key, Object object) {
    checkingComponent(object);
    components.put(key, object);
  }

  @Override
  public void registerObjectToComponentFactory(String key, Object object) {
    components.put(key, object);
  }

  @Override
  public void removeObjectFromComponentFactory(String key) {
    components.remove(key);
  }

}
