package org.jiakesimk.minipika.framework.factory;

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

import org.jiakesimk.minipika.components.cache.SetCache;
import org.jiakesimk.minipika.components.cache.WeakCacheImpl;
import org.jiakesimk.minipika.components.jdbc.ConstResultSet;
import org.jiakesimk.minipika.components.jdbc.NativeJdbcImpl;
import org.jiakesimk.minipika.components.jdbc.NativeResultSet;
import org.jiakesimk.minipika.components.jdbc.transaction.JdbcTransaction;
import org.jiakesimk.minipika.components.jdbc.transaction.JdbcTransactionFactory;
import org.jiakesimk.minipika.components.jdbc.transaction.TransactionFactory;
import org.jiakesimk.minipika.framework.util.ClassUtils;
import org.jiakesimk.minipika.framework.util.Maps;

import java.util.Map;

/**
 * @author tiansheng
 */
@SuppressWarnings("unchecked")
public class BuiltinComponentFactory implements ComponentFactory {

  /**
   * 组件容器
   */
  private static final Map<String, Object> components = Maps.newConcurrentHashMap(8);

  private static ComponentFactory factory;

  private BuiltinComponentFactory() {
    {
      components.put(NativeResultSet.class.getName(), forClass(ConstResultSet.class));
      components.put(NativeJdbcImpl.class.getName(), forClass(NativeJdbcImpl.class));
      components.put(JdbcTransaction.class.getName(), forClass(JdbcTransaction.class));
      components.put(TransactionFactory.class.getName(), forClass(JdbcTransactionFactory.class));
      components.put(SetCache.class.getName(), forClass(WeakCacheImpl.class));
    }
  }

  public static ComponentFactory getFactory() {
    if (factory == null) {
      factory = new BuiltinComponentFactory();
    }
    return factory;
  }

  @Override
  public <T> T forClass(Class<?> clazz) {
    return forClass(clazz, null);
  }

  @Override
  public <T> T forClass(Class<?> clazz, Class<?>[] types, Object... parameter) {
    try {
      Object component = null;
      String className = clazz.getName();
      component = components.get(className);
      if (component != null) {
        return (T) component;
      }
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
      } else {
        component = InjectUtils.minipika(clazz, types, components, parameter);
      }

      // 如果当前实例化出来的组件不是null, 就添加到组件容器中
      if (component != null) {
        components.put(className, component);
      }
      return (T) component;
    } catch (IllegalAccessException e) {
      e.printStackTrace();
      return null;
    }
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
      if (interfaces.length != 0) {
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
