package task

import javassist.*
import javassist.bytecode.CodeAttribute
import javassist.bytecode.LocalVariableAttribute
import javassist.bytecode.MethodInfo
import org.codehaus.groovy.control.CompilerConfiguration
import org.jiakesiws.minipika.components.logging.Log
import org.jiakesiws.minipika.components.logging.LogFactory
import org.jiakesiws.minipika.framework.common.ConstVariable
import org.jiakesiws.minipika.framework.exception.MinipikaException
import org.jiakesiws.minipika.framework.thread.Threads
import org.jiakesiws.minipika.framework.utils.Lists

import java.lang.annotation.Annotation
import java.lang.reflect.Method
import java.lang.reflect.Parameter

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
class Methods {

  static String[] getParameterNames(Method method) {
    List<String> names = Lists.newArrayList()
    Parameter[] parametersArray = method.getParameters()
    if (parametersArray != null && parametersArray.length != 0) {
      String parameterName = parametersArray[0].getName()
      // 如果是var0 | arg0代表无法获取到字节码中的方法参数名称
      if ("var0" != parameterName && "arg0" != parameterName) {
        for (Parameter parameter : parametersArray) {
          names.add(parameter.getName())
        }
      } else {
        // 使用字节码工具获取源码中的参数名
        // 如果还是没获取到的话就使用GroovyClassLoader获取
        Class declaringClass = method.declaringClass
        String[] ns = getMethodVariableName(declaringClass.name, method.getName())
        if (ns == null) {
          GroovyClassLoader loader = ConstVariable.groovyClassLoader
          Class clazz = loader.parseClass(new File("src/main/java/${declaringClass.package.name}/${declaringClass.simpleName}.groovy"))
          Method newMethod = clazz.getDeclaredMethod(method.name, method.getParameterTypes())
          if (newMethod != null) {
            Parameter[] parameters = newMethod.parameters
            ns = new String[parameters.length]
            parameters.eachWithIndex { it, index ->
              ns[index] = it.name
            }
          } else {
            throw new MinipikaException("Error parameters name of method cannot obtained. Cause: null")
          }
        }
        return ns
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
                                        Class<?>... parameterTypes) {
    try {
      ClassPool pool = ConstVariable.CLASS_POOL
      pool.insertClassPath(new ClassClassPath(Threads.getCaller()))
      CtClass cc = pool.get(classname)
      CtMethod cm = cc.getDeclaredMethod(methodname)
      MethodInfo methodInfo = cm.getMethodInfo()
      CodeAttribute codeAttribute = methodInfo.getCodeAttribute()
      String[] paramNames = new String[cm.getParameterTypes().length]
      LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag)
      if (attr != null) {
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1
        for (int i = 0; i < paramNames.length; i++) {
          paramNames[i] = attr.variableName(i + pos)
        }
        return paramNames
      }
    } catch (Exception e) {
      return null
    }
  }

}
