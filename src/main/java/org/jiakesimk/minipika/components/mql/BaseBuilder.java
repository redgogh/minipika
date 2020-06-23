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
import javassist.NotFoundException;
import org.jiakesimk.minipika.framework.common.ConstVariable;
import org.jiakesimk.minipika.framework.compiler.JavaCompiler;
import org.jiakesimk.minipika.framework.util.ClassUtils;
import org.jiakesimk.minipika.framework.util.Matches;
import org.jiakesimk.minipika.framework.util.Methods;
import org.jiakesimk.minipika.framework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * @author tiansheng
 */
public class BaseBuilder extends Invoker {

  private final String classname;

  private final StringBuilder mtClass = new StringBuilder();

  public BaseBuilder(String classname) {
    int lastIndexOf = classname.lastIndexOf(".");
    mtClass.append("package ").append(classname, 0, lastIndexOf).append(";");
    mtClass.append("import java.lang.*;");
    mtClass.append("import java.util.*;");
    mtClass.append("import java.lang.*;");
    mtClass.append("import java.math.*;");
    mtClass.append("import org.jiakesimk.minipika.framework.util.StringUtils;");
    mtClass.append("@SuppressWarnings(\"unchecked\")");
    mtClass.append("public class ").append(classname, lastIndexOf + 1, classname.length()).append("{}");
    this.classname = classname;
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
    finalSrc.delete(finalSrc.length() - 1, finalSrc.length());
    finalSrc.delete(finalSrc.length(), finalSrc.length()).append("){"); // 方法头部声明结尾
    // 构建方法体
    buildBody(src, finalSrc);
    finalSrc.append("Object[] objects = new Object[2];");
    finalSrc.append("objects[0] = sql.toString();");
    finalSrc.append("objects[1] = arguments;");
    finalSrc.append("return objects;}");
    mtClass.insert(mtClass.length() - 1, finalSrc);
    System.out.println(mtClass);
  }

  /**
   * 构建方法体
   *
   * @param src     动态sql
   * @param builder StringBuilder引用
   */
  private void buildBody(String src, StringBuilder builder) {
    builder.append("StringBuilder sql = new StringBuilder();");
    builder.append("List arguments = new LinkedList();");
    String[] single = src.split("\n");
    for (String input : single) {
      input = input.trim();
      // 判断当前行是不是普通的SQL语句
      if (StringUtils.isNotEmpty(input) && input.charAt(0) != '#') {
        builder.append("sql.append(\"").append(input.replaceAll("#\\{(.*?)}", "?"))
                .append(" ").append("\");");
        String[] arguments = existArguments(input);
        for (String argument : arguments) {
          builder.append("arguments.add(").append(argument).append(");");
        }
      } else {
        if (input.contains("#if")) {
          input = input.replace("#if", "").trim();
          input = "".concat("if(").concat(parseIfStatement(input)).concat("){");
          builder.append(input);
        }
        if (input.contains("#end")) {
          builder.append("}");
        }
      }
    }
  }

  /**
   * 解析if语句
   *
   * @param input if语句块
   */
  private String parseIfStatement(String input) {
    input = input.concat(" ");
    char[] charArray = input.toCharArray();
    StringBuilder builder = new StringBuilder();
    StringBuilder temp = new StringBuilder();
    for (char ch : charArray) {
      if (('a' <= ch && 'z' >= ch) ||
              ('A' <= ch && 'Z' >= ch) ||
              (ch == '_' || ch == '$' || ch == '.')) {
        temp.append(ch);
      } else {
        String content = invokeToAddGet(temp.toString());
        builder.append(content);
        builder.append(ch);
        temp.delete(0, builder.length());
      }
    }
    return builder.toString();
  }

  /**
   * 调用时特殊处理
   *
   * @param input
   * @return
   */
  private String invokeToAddGet(String input) {
    if (ConstVariable.IEE.equals(input)) {
      return "StringUtils.isEmpty";
    }
    if (ConstVariable.INE.equals(input)) {
      return "StringUtils.isNotEmpty";
    }
    if (input.contains(".")) {
      String[] idens = input.split("\\.");
      input = idens[0];
      for (int j = 1; j < idens.length; j++) {
        input = input.concat(".").concat("get").concat(StringUtils.toUpperCase(idens[j], 1)).concat("()");
      }
    }
    return input;
  }

  /**
   * 是否存在参数
   *
   * @param input
   * @return
   */
  @SuppressWarnings({"rawtypes"})
  private String[] existArguments(String input) {
    return Matches.find(input, "#\\{(.*?)}\\S+", new Closure(null) {
      @Override
      public Object call(Object... args) {
        String args0 = (String) args[0];
        args0 = args0.replaceAll("#", "");
        if (args0.contains(".")) {
          String[] split = args0.split("\\.");
          for (int i = 1; i < split.length; i++) {
            args0 = invokeToAddGet(args0);
          }
        }
        return args0;
      }
    });
  }

  protected void end() {
    instance = ClassUtils.newInstance(JavaCompiler.compile(classname, mtClass.toString()));
  }

}
