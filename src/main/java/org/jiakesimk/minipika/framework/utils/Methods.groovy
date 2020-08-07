package org.jiakesimk.minipika.framework.utils

import javassist.*
import javassist.bytecode.CodeAttribute
import javassist.bytecode.LocalVariableAttribute
import javassist.bytecode.MethodInfo
import org.codehaus.groovy.control.CompilerConfiguration
import org.jiakesimk.minipika.components.logging.Log
import org.jiakesimk.minipika.components.logging.LogFactory
import org.jiakesimk.minipika.framework.common.ConstVariable
import org.jiakesimk.minipika.framework.exception.MinipikaException
import org.jiakesimk.minipika.framework.thread.Threads

import javax.management.ReflectionException
import java.lang.annotation.Annotation
import java.lang.reflect.Method
import java.lang.reflect.Parameter
import java.util.List

/* ************************************************************************
 *
 * Copyright (C) 2020 2B键盘 All rights reserved.
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
 *
 * ************************************************************************/

/*
 * Creates on 2020/6/16.
 */

/**
 * @author 2B键盘* @email jiakesiws@gmail.com
 */
class Methods
{

  private static Log LOG = LogFactory.getLog(Methods.class)

  /**
   * 通过{@link Class}实例获取方法。
   */
  static Method[] getMethods(Class<?> target)
  {
    return getMethods(target, false)
  }

  /**
   * 通过{@link Class}实例获取方法。
   */
  static Method[] getMethods(Class<?> target, boolean accessible)
  {
    Method[] methods = target.getMethods()
    methods.each {
      setAccessible(it, accessible)
    }
    return methods
  }

  /**
   * 通过{@link Class}实例获取声明的方法。
   */
  static Method[] getDeclaredMethods(Class<?> target)
  {
    return getDeclaredMethods(target, false)
  }

  /**
   * 通过{@link Class}实例获取声明的方法。
   */
  static Method[] getDeclaredMethods(Class<?> target, boolean accessible)
  {
    Method[] methods = target.getDeclaredMethods()
    for (Method method : methods)
    {
      setAccessible(method, accessible)
    }
    return methods
  }

  /**
   * 如果{@param method}具有{@param annotations}，则返回当前注解实例。
   */
  static <T extends Annotation> T isAnnotation(Method method, Class<T> annotations)
  {
    if (method.isAnnotationPresent(annotations))
    {
      return method.getDeclaredAnnotation(annotations)
    }
    return null
  }

  /**
   * 设置方法访问参数
   */
  static void setAccessible(Method method)
  {
    method.setAccessible(true)
  }

  /**
   * 设置方法访问参数
   */
  static void setAccessible(Method method, boolean flag)
  {
    method.setAccessible(flag)
  }

  /**
   * 获取方法参数真实名称
   *
   * @param method 方法对象
   * @return 方法名称，根据顺序排序
   */
  static String[] getParameterNames(Method method)
  {
    List<String> names = Lists.newArrayList()
    Parameter[] parametersArray = method.getParameters()
    if (parametersArray != null && parametersArray.length != 0)
    {
      String parameterName = parametersArray[0].getName()
      // 如果是var0 | arg0代表无法获取到字节码中的方法参数名称
      if ("var0" != parameterName && "arg0" != parameterName)
      {
        for (Parameter parameter : parametersArray)
        {
          names.add(parameter.getName())
        }
      } else
      {
        // 使用字节码工具获取源码中的参数名
        // 如果还是没获取到的话就使用GroovyClassLoader获取
        String methodName = method.name
        Class declaringClass = method.declaringClass
        String[] nameArray = getMethodVariableName(declaringClass.name, methodName)
        if (nameArray == null)
        {
          GroovyClassLoader loader = ConstVariable.groovyClassLoader
          Class clazz = loader.parseClass(new File("src/main/java/${declaringClass.package.name}/${declaringClass.simpleName}.groovy"))
          Method newMethod = clazz.getDeclaredMethod(methodName, method.getParameterTypes())
          if (newMethod != null)
          {
            Parameter[] parameters = newMethod.parameters
            nameArray = new String[parameters.length]
            parameters.eachWithIndex { it, index ->
              nameArray[index] = it.name
            }
          } else
          {
            throw new MinipikaException("Error parameters name of method cannot obtained. Cause: null")
          }
        }
        return nameArray
      }
    }
    return names.toArray(new String[names.size()])
  }

  /**
   * 使用Javassist获取方法参数名
   *
   * @param classname 方法全路径
   * @param methodname 方法名
   * @return 方法参数名
   */
  static String[] getMethodVariableName(String classname, String methodname,
                                        Class<?>... parameterTypes)
  {
    try
    {
      ClassPool pool = ConstVariable.CLASS_POOL
      pool.insertClassPath(new ClassClassPath(Threads.getCaller()))
      CtClass cc = pool.get(classname)
      CtMethod cm = cc.getDeclaredMethod(methodname)
      MethodInfo methodInfo = cm.getMethodInfo()
      CodeAttribute codeAttribute = methodInfo.getCodeAttribute()
      String[] paramNames = new String[cm.getParameterTypes().length]
      LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag)
      if (attr != null)
      {
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1
        for (int i = 0; i < paramNames.length; i++)
        {
          paramNames[i] = attr.variableName(i + pos)
        }
        return paramNames
      }
    } catch (Exception e)
    {
      LOG.error("getMethodVariableName fail", e)
    }
    return null
  }

  /**
   * 获取方法签名
   *
   * @param method 需要获取签名的方法
   * @return 方法签名
   */
  static String getGenericReturnType(Method method)
  {
    return method.genericReturnType
  }

}
