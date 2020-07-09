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

import org.jiakesimk.minipika.framework.annotations.Inject;
import org.jiakesimk.minipika.framework.util.Annotations;
import org.jiakesimk.minipika.framework.util.ClassUtils;
import org.jiakesimk.minipika.framework.util.Fields;
import org.jiakesimk.minipika.framework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;

/**
 * 自动注入工具类
 *
 * @author tiansheng
 */
@SuppressWarnings({"unchecked"})
public class InjectUtils {

  public static Object autowired(Class<?> clazz, Map<String, Object> components) throws IllegalAccessException {
    Object instance = ClassUtils.newInstance(clazz);
    return autowired(clazz, instance, components);
  }

  public static Object autowired(Class<?> clazz, Class<?>[] types, Map<String, Object> components,
                                 Object... parameter) throws IllegalAccessException {
    Object instance = ClassUtils.newInstance(clazz, types, parameter);
    return autowired(clazz, instance, components);
  }

  private static Object autowired(Class<?> clazz, Object instance, Map<String, Object> components)
          throws IllegalAccessException {
    Field[] fields = Fields.getDeclaredFieldsIncludeSuper(clazz, true, new Class[]{Inject.class});
    for (Field field : fields) {
      Inject inject = Annotations.isAnnotation(field, Inject.class);
      String name = inject.name();
      if (StringUtils.isEmpty(name)) {
        name = field.getName();
      }
      Object object = components.get(field.getType().getName());
      field.set(instance, Objects.requireNonNull(object,
              "Error unable initialize component named " + name)); // 注入对象
    }
    return instance;
  }

}
