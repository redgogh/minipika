package org.jiakesimk.minipika.components.mql;

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
 * Creates on 2020/6/22.
 */

import groovy.lang.Closure;
import javassist.*;
import org.jiakesimk.minipika.framework.common.ConstVariable;
import org.jiakesimk.minipika.framework.util.Matches;
import org.jiakesimk.minipika.framework.util.Methods;
import org.jiakesimk.minipika.framework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author tiansheng
 */
public class BaseBuilder {

  private CtClass cc;

  private Class<?> statement;

  private ClassPool pool = ConstVariable.CLASS_POOL;

  public BaseBuilder(Class<?> statement) {
    this.statement = statement;
    cc = pool.makeClass(ConstVariable.MQL_PROXY_CLASSNAME.concat(statement.getSimpleName()));
  }

  /**
   * 创建一个方法
   *
   * @param method 方法名
   * @param src    动态sql
   * @return {@code CtMethod}
   */
  protected void createMethod(Method method, String src)
          throws NotFoundException {
    StringBuilder finalSrc = new StringBuilder();
    String methodName = method.getName(); // 方法名
    String[] paramNames = Methods.getParameterNames(method); // 方法参数名
    Class<?>[] paramTypes = method.getParameterTypes();
    // 创建方法声明
    finalSrc.append("public Object[] ").append(methodName).append("(");
    for (int i = 0; i < paramNames.length; i++) {
      finalSrc.append(paramTypes[i].getName()).append(" ").append(paramNames[i]);
      finalSrc.append(",");
    }
    finalSrc.delete(finalSrc.length() - 1, finalSrc.length()).append("){"); // 方法头部声明结尾
    // 构建方法体
    buildBody(src, finalSrc);
    // CtMethod ctMethod = CtMethod.make(finalSrc.toString(), cc);
  }

  /**
   * 构建方法体
   *
   * @param src     动态sql
   * @param builder StringBuilder引用
   */
  private void buildBody(String src, StringBuilder builder) {
    builder.append("StringBuilder sql = new StringBuilder();");
    builder.append("List<Object> arguments = new LinkedList<>();");
    String[] single = src.split("\n");
    for (String input : single) {
      input = input.trim();
      // 判断当前行是不是普通的SQL语句
      if (StringUtils.isNotEmpty(input) && input.charAt(0) != '#') {
        builder.append("sql.append(\"").append(input).append("\");");
        String[] arguments = existArguments(input);
        for (String argument : arguments) {
          builder.append("arguments.add(").append(argument).append(");");
        }
      } else {
        if(input.contains("#if")) {
          input = input.replace("#if", "");
          System.out.println();
        }
        if(input.contains("#end")) {
          builder.append("}");
        }
      }
    }
    System.out.println(builder.toString());
  }

  /**
   * 是否存在参数
   *
   * @param input
   * @return
   */
  private String[] existArguments(String input) {
    return Matches.find(input, "#(.*?)\\S+", new Closure(null) {
      @Override
      public Object call(Object... args) {
        String args0 = (String) args[0];
        args0 = args0.replaceAll("#", "");
        if (args0.contains(".")) {
          String[] split = args0.split("\\.");
          for (int i = 1; i < split.length; i++) {
            String s = split[i];
            s = s.concat("()");
            args0 = args0.concat(".").concat(StringUtils.toUpperCase(s, 1));
          }
        }
        return args0;
      }
    });
  }

}
