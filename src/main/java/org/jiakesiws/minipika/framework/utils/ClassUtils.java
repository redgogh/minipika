package org.jiakesiws.minipika.framework.utils;

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
 * Creates on 2020/3/21.
 */

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * 类的静态工具类
 *
 * @author lts
 * @email jiakesiws@gmail.com
 */
@SuppressWarnings("unchecked")
public class ClassUtils {

  /**
   * 创建一个实例。
   */
  public static <T> T newInstance(Class<?> clazz) {
    try {
      Constructor<?> constructor = clazz.getDeclaredConstructor();
      return (T) constructor.newInstance();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 创建一个实例并传递构造函数参数。
   *
   * @param parametersType  构造函数参数类型。
   * @param parametersValue 构造函数参数值。
   */
  public static <T> T newInstance(Class<?> clazz, Class<?>[] parametersType,
                                   Object... parametersValue) {
    Constructor<?> constructor = null;
    try {
      constructor = clazz.getConstructor(parametersType);
      return (T) constructor.newInstance(parametersValue);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 获取所有父类
   */
  public static List<Class<?>> getSuperClasses(Class<?> target) {
    List<Class<?>> classes = Lists.newArrayList();
    getSuperClasses(target, classes);
    return classes;
  }

  /**
   * 获取所有父类递归方法
   */
  static void getSuperClasses(Class<?> target, List<Class<?>> classes) {
    Class<?> superclass = target.getSuperclass();
    if (superclass == null) return;
    classes.add(superclass);
    getSuperClasses(superclass, classes);
  }

  /**
   * 判断是不是接口
   */
  public static boolean isInterface(Class<?> target) {
    return target != null && target.isInterface();
  }

}
