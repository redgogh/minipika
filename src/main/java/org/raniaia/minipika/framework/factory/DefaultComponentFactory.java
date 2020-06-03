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

  private static final ComponentFactory factory = new DefaultComponentFactory();

  public static ComponentFactory getFactory() {
    return factory;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T forClass(Class<?> clazz) {
    try {
      return (T) InjectUtils.minipika(clazz, components);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public <T> T forClass(Class<?> clazz, Class<?>[] types, Object... parameter) {
    try {
      return (T) InjectUtils.minipika(clazz, types, components, parameter);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
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
