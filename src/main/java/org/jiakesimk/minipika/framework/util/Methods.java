package org.jiakesimk.minipika.framework.util;

import com.github.houbb.asm.tool.reflection.AsmMethods;
import org.jiakesimk.minipika.framework.exception.MinipikaException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;

/*
 * Copyright (C) 2020 tiansheng All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
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
 * Creates on 2020/6/16.
 */

/**
 * @author tiansheng
 */
public class Methods {

  /**
   * 通过{@link Class}实例获取方法。
   */
  public static Method[] getMethods(Class<?> target) {
    return getMethods(target, false);
  }

  /**
   * 通过{@link Class}实例获取方法。
   */
  public static Method[] getMethods(Class<?> target, boolean accessible) {
    Method[] methods = target.getMethods();
    for (Method method : methods) {
      setAccessible(method, accessible);
    }
    return methods;
  }

  /**
   * 通过{@link Class}实例获取声明的方法。
   */
  public static Method[] getDeclaredMethods(Class<?> target) {
    return getDeclaredMethods(target, false);
  }

  /**
   * 通过{@link Class}实例获取声明的方法。
   */
  static Method[] getDeclaredMethods(Class<?> target, boolean accessible) {
    Method[] methods = target.getDeclaredMethods();
    for (Method method : methods) {
      setAccessible(method, accessible);
    }
    return methods;
  }

  /**
   * 如果{@param method}具有{@param annotations}，则返回当前注解实例。
   */
  static <T extends Annotation> T isAnnotation(Method method, Class<T> annotations) {
    if (method.isAnnotationPresent(annotations)) {
      return method.getDeclaredAnnotation(annotations);
    }
    return null;
  }

  /**
   * 设置方法访问参数
   */
  static void setAccessible(Method method) {
    method.setAccessible(true);
  }

  /**
   * 设置方法访问参数
   */
  static void setAccessible(Method method, boolean flag) {
    method.setAccessible(flag);
  }

  /**
   * 获取方法参数真实名称
   *
   * @param method 方法对象
   * @return 方法名称，根据顺序排序
   */
  public static String[] getParameterNames(Method method) {
    List<String> names = Lists.newArrayList();
    Parameter[] parametersArray = method.getParameters();
    if (parametersArray != null && parametersArray.length != 0) {
      String parameterName = parametersArray[0].getName();
      if (!"var0".equals(parameterName)) {
        for (Parameter parameter : parametersArray) {
          names.add(parameter.getName());
        }
      }
    }
    return names.toArray(new String[names.size()]);
  }

  /**
   * +
   * 获取{@link Method}的参数{@link Parameter}数组, 其中会获取
   * 真实名称
   *
   * @param method 方法对象
   * @return {@code Parameter}数组
   */
  static Map<String, Parameter> getParameters(Method method) {
    try {
      Map<String, Parameter> map = Maps.newHashMap();
      List<String> strValue = AsmMethods.getParamNamesByAsm(method);
      Parameter[] parameters = method.getParameters();
      for (int i = 0; i < parameters.length; i++) {
        Fields.set(parameters[i], strValue.get(i), "name");
        map.put(strValue.get(i), parameters[i]);
      }
      return map;
    } catch (Exception e) {
      e.printStackTrace();
      throw new MinipikaException(e);
    }
  }

  /**
   * 获取方法签名
   *
   * @param method 需要获取签名的方法
   * @return 方法签名
   */
  public static String getGenericSignature(Method method) {
    String signature = null;
    try {
      Method declaredMethod = Method.class.getDeclaredMethod("getGenericSignature");
      declaredMethod.setAccessible(true);
      signature = (String) declaredMethod.invoke(method);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return signature;
  }

}
