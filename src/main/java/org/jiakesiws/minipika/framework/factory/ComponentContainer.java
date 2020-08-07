package org.jiakesiws.minipika.framework.factory;

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
 * Creates on 2020/6/1.
 */

import org.jiakesiws.minipika.components.groovy.builder.Configuration;
import org.jiakesiws.minipika.components.groovy.builder.Behalf;
import org.jiakesiws.minipika.framework.utils.ClassUtils;
import org.jiakesiws.minipika.framework.utils.Maps;

import java.util.Map;

/**
 * @author 2B键盘
 * @email jiakesiws@gmail.com
 */
@SuppressWarnings("unchecked")
public class ComponentContainer implements ComponentFactory
{

  /**
   * 组件容器
   */
  static final Map<String, Object> components = Maps.newConcurrentHashMap();

  static final ComponentFactory factory = new ComponentContainer();

  private ComponentContainer()
  {
  }

  @Override
  public <T> T forClass(Class<?> clazz)
  {
    return forClass(clazz, null);
  }

  @Override
  public <T> T forClass(Class<?> clazz, Class<?>[] types, Object... parameter)
  {
    try
    {
      Object component = null;
      String className = clazz.getName();

      component = components.get(className);

      if (component instanceof Class)
      {
        component = InjectUtils.autowired((Class<?>) component);
      }

      if (component != null) return (T) component;


      if (ClassUtils.isInterface(clazz))
      {
        // 如果是接口的话那么就查找匹配接口类型的类
        component = findMatchesClassType(clazz);
        if (component != null)
        {
          return (T) component;
        }
      }


      // 判断是否执行有参构造器
      if (types == null)
      {
        component = InjectUtils.autowired(clazz);
      } else
      {
        component = InjectUtils.autowired(clazz, types, parameter);
      }

      // 如果当前实例化出来的组件不是null并且不是方法的配置信息的话, 就添加到组件容器中
      if (component != null && !(component instanceof Configuration) && !(component instanceof Behalf))
      {
        components.put(className, component);
      }

      return (T) component;
    } catch (IllegalAccessException e)
    {
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
  private static Object findMatchesClassType(Class<?> IFACE)
  {
    for (Object value : components.values())
    {
      Class<?>[] interfaces = value.getClass().getInterfaces();
      if (interfaces.length != 0)
      {
        for (Class<?> IFACES : interfaces)
        {
          if (IFACE == IFACES)
          {
            return value;
          }
        }
      }
    }
    return null;
  }

  @Override
  public void reloading(String key, Object object)
  {
    checkingComponent(object);
    components.put(key, object);
  }

  public static Map<String, Object> getComponents()
  {
    return components;
  }

  @Override
  public void registerObjectToComponentFactory(String key, Object object)
  {
    components.put(key, object);
  }

  @Override
  public void removeObjectFromComponentFactory(String key)
  {
    components.remove(key);
  }

}
