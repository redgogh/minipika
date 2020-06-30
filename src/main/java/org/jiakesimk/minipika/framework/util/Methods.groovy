package org.jiakesimk.minipika.framework.util

import com.github.houbb.asm.tool.reflection.AsmMethods
import javassist.ClassPool
import javassist.CtClass
import javassist.CtMethod
import javassist.Modifier
import javassist.NotFoundException
import javassist.bytecode.CodeAttribute
import javassist.bytecode.LocalVariableAttribute
import javassist.bytecode.MethodInfo
import lombok.SneakyThrows
import org.jiakesimk.minipika.framework.common.ConstVariable
import org.jiakesimk.minipika.framework.exception.MinipikaException

import java.lang.annotation.Annotation
import java.lang.reflect.Method
import java.lang.reflect.Parameter

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
class Methods {

  static def pool = ConstVariable.CLASS_POOL

  /**
   * 通过{@link Class}实例获取方法。
   */
  static Method[] getMethods(Class<?> target) {
    return getMethods(target, false)
  }

  /**
   * 通过{@link Class}实例获取方法。
   */
  static Method[] getMethods(Class<?> target, boolean accessible) {
    Method[] methods = target.getMethods()
    methods.each { method ->
      setAccessible(method, accessible)
    }
    return methods
  }

  /**
   * 通过{@link Class}实例获取声明的方法。
   */
  static Method[] getDeclaredMethods(Class<?> target) {
    return getDeclaredMethods(target, false)
  }

  /**
   * 通过{@link Class}实例获取声明的方法。
   */
  static Method[] getDeclaredMethods(Class<?> target, boolean accessible) {
    Method[] methods = target.getDeclaredMethods()
    methods.each { method -> setAccessible(method, accessible) }
    return methods
  }

  /**
   * 如果{@param method}具有{@param annotations}，则返回当前注解实例。
   */
  static <T extends Annotation> T isAnnotation(Method method, Class<T> annotations) {
    if (method.isAnnotationPresent(annotations)) {
      return method.getDeclaredAnnotation(annotations)
    }
    return null
  }

  /**
   * 根据注解来获取方法实例。
   */
  static Method[] getMethodsByAnnotations(Class<?> target,
                                          Class<? extends Annotation>[] annotations) {
    List<Method> methodList = Lists.newArrayList()
    Method[] methods = getDeclaredMethods(target)
    methods.each { method ->
      annotations.each { annotation ->
        if (method.isAnnotationPresent(annotation)) {
          methodList.add(method)
        }
      }
    }
    return methodList.toArray() as Method[]
  }

  static Object invoke(Method method, Object... args) {
    Class clazz = method.getDeclaringClass()
    Object instance = ClassUtils.newInstance(clazz)
    return method.invoke(instance, args)
  }

  static Object invoke(Method method, boolean accessible, Object... args) {
    setAccessible(method, accessible)
    Class clazz = method.getDeclaringClass()
    Object instance = ClassUtils.newInstance(clazz)
    return method.invoke(instance, args)
  }


  /**
   * 设置方法访问参数
   */
  static void setAccessible(Method method) {
    method.setAccessible(true)
  }

  /**
   * 设置方法访问参数
   */
  static void setAccessible(Method method, boolean flag) {
    method.setAccessible(flag)
  }

  /**
   * 获取方法参数真实名称
   *
   * @param method 方法对象
   * @return 方法名称，根据顺序排序
   */
  static String[] getParameterNames(Method method) throws NotFoundException {
    CtClass ctClass = pool.get(method.getDeclaringClass().getName())
    def parameters = []
    method.getParameterTypes().each { paramType ->
      parameters.add(pool.get(paramType.getName()))
    }
    CtMethod ctMethod = ctClass.getDeclaredMethod(method.getName(), parameters as CtClass[])
    MethodInfo methodInfo = ctMethod.getMethodInfo()
    CodeAttribute codeAttribute = methodInfo.getCodeAttribute()
    String[] paramNames = new String[ctMethod.getParameterTypes().length]
    LocalVariableAttribute attr = codeAttribute.getAttribute(LocalVariableAttribute.tag) as LocalVariableAttribute
    if (attr != null) {
      int pos = Modifier.isStatic(ctMethod.getModifiers()) ? 0 : 1
      for (int i = 0; i < paramNames.length; i++) {
        paramNames[i] = attr.variableName(i + pos)
      }
    }
    return paramNames
  }

  /**
   * 获取{@link Method}的参数{@link Parameter}数组, 其中会获取
   * 真实名称
   *
   * @param method 方法对象
   * @return {@code Parameter}数组
   */
  static Map<String, Parameter> getParameters(Method method) {
    try {
      Map<String, Parameter> map = Maps.newHashMap()
      List<String> strValue = AsmMethods.getParamNamesByAsm(method)
      Parameter[] parameters = method.getParameters();
      for (int i = 0; i < parameters.length; i++) {
        Fields.set(parameters[i], strValue.get(i), "name")
        map.put strValue.get(i), parameters[i]
      }
      return map;
    } catch (Exception e) {
      e.printStackTrace();
      throw new MinipikaException(e);
    }
  }

}
