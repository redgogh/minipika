package org.jiakesimk.minipika.framework.factory;

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
 * Creates on 2020/6/1.
 */

import org.jiakesimk.minipika.framework.annotations.Component;
import org.jiakesimk.minipika.framework.common.ProxyHandler;
import org.jiakesimk.minipika.framework.utils.Annotations;
import org.jiakesimk.minipika.framework.utils.ClassUtils;
import org.jiakesimk.minipika.framework.utils.Fields;
import org.jiakesimk.minipika.framework.utils.StringUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;

/**
 * 自动注入工具类
 *
 * @author tiansheng
 * @email jiakesiws@gmail.com
 */
@SuppressWarnings({"unchecked"})
public class InjectUtils {

  private static final Class<?> IFACE = ProxyHandler.class;

  public static Object autowired(Class<?> clazz) throws IllegalAccessException {
    return autowired(clazz, null, 0);
  }

  public static Object autowired(Class<?> clazz, Class<?>[] types,
                                 Object... parameter) throws IllegalAccessException {
    Object instance;
    if (types == null) {
      instance = ClassUtils.newInstance(clazz);
    } else {
      instance = ClassUtils.newInstance(clazz, types, parameter);
    }
    return autowired(clazz, instance);
  }

  private static Object autowired(Class<?> clazz, Object instance)
          throws IllegalAccessException {
    Map<String, Object> components = ComponentContainer.components;
    Field[] fields = Fields.getDeclaredFieldsIncludeSuper(clazz, true, new Class[]{Component.class});
    for (Field field : fields) {
      Component component = Annotations.isAnnotation(field, Component.class);
      String name = component.name();
      if (StringUtils.isEmpty(name)) {
        name = field.getName();
      }
      Object object = components.get(field.getType().getName());
      field.set(instance, Objects.requireNonNull(object,
              "Error unable initialize component named " + name)); // 注入对象
    }
    return getProxyHandler(instance, clazz);
  }

  /**
   * 判断当前类有没有代理程序
   *
   * @param instance 实例对象
   * @param clazz    类信息
   * @return 如果有代理程序返回代理, 没有返回instance
   */
  private static Object getProxyHandler(Object instance, Class<?> clazz) {
    Class<?>[] ifaces = clazz.getInterfaces();
    for (Class<?> iface : ifaces) {
      if (iface == IFACE) {
        ProxyHandler handler = (ProxyHandler) instance;
        return handler.getProxyHandler();
      }
    }
    return instance;
  }

}
