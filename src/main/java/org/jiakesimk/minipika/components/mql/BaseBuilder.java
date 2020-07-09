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
import org.jiakesimk.minipika.framework.compiler.JavaCompiler;
import org.jiakesimk.minipika.components.logging.Log;
import org.jiakesimk.minipika.components.logging.LogFactory;
import org.jiakesimk.minipika.framework.util.ClassUtils;
import org.jiakesimk.minipika.framework.util.Matches;
import org.jiakesimk.minipika.framework.util.Methods;
import org.jiakesimk.minipika.framework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author tiansheng
 */
public class BaseBuilder extends Invoker {

  private final String classname;

  private final StringBuilder mtClass = new StringBuilder();

  private static final Log LOG = LogFactory.getLog(BaseBuilder.class);

  private static final String IF = "#IF";

  private static final String END = "#END";

  private static final String FOREACH = "#FOREACH";

  private static final String IS_NOT_EMPTY = "INE";

  private static final String IS_EQUALS_EMPTY = "IE";

  //
  // 用于识别当前是不是解析到foreach语句
  // 如果是的话foreach语句内容需要特殊处理
  //
  private boolean foreach = false;

  //
  // 构建方法的StringBuilder, 最后会将它设置为null
  //
  private static StringBuilder builder = new StringBuilder();

  public BaseBuilder(String classname) {
    int lastIndexOf = classname.lastIndexOf(".");
    mtClass.append("package ").append(classname, 0, lastIndexOf).append(";");
    mtClass.append("import java.lang.*;");
    mtClass.append("import java.util.*;");
    mtClass.append("import java.lang.*;");
    mtClass.append("import java.math.*;");
    mtClass.append("import org.jiakesimk.minipika.framework.util.*;");
    mtClass.append("import org.jiakesimk.minipika.framework.util.agent.*;");
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
    StringUtils.clear(builder);
    String methodName = method.getName(); // 方法名
    String[] paramNames = Methods.getParameterNames(method); // 方法参数名
    Class<?>[] paramTypes = method.getParameterTypes();
    // 创建方法声明
    builder.append("public Object[] ").append(methodName).append("(");
    for (int i = 0; i < paramNames.length; i++) {
      builder.append(paramTypes[i].getName()).append(" ").append(paramNames[i]);
      builder.append(",");
    }
    builder.delete(builder.length() - 1, builder.length());
    builder.delete(builder.length(), builder.length()).append("){"); // 方法头部声明结尾
    // 构建方法体
    buildBody(src, method);
    builder.append("Object[] objects = new Object[2];");
    builder.append("objects[0] = sql.toString();");
    builder.append("objects[1] = arguments;");
    builder.append("return objects;}");
    mtClass.insert(mtClass.length() - 1, builder);
    // -- debug使用
    // System.out.println(mtClass);
  }

  /**
   * 构建方法体
   *
   * @param src 动态sql
   */
  private void buildBody(String src, Method method) throws NotFoundException {
    _buildHead();
    String[] single = src.split("\n");
    for (String line : single) {
      line = line.trim();

      if (line.contains(IF)) {
        _if(line);
        continue;
      }

      if (line.contains(FOREACH)) {
        _foreach(line);
        continue;
      }

      if (line.contains(END)) {
        _end();
        continue;
      }

      {
        line = _check(line);
        if (foreach) {
          _eachContent(line);
        } else {
          _sqlparse(line);
        }
      }
    }
  }

  /**
   * 构建方法所需的成员
   */
  private void _buildHead() {
    builder.append("StringBuilder sql = new StringBuilder();");
    builder.append("List arguments = new LinkedList();");
  }

  /**
   * 构建if语句
   */
  private void _if(String line) {
    line = line.replace(IF, "").trim();
    line = "".concat("if(").concat(parseIfStatement(line)).concat("){");
    builder.append(line);
  }

  /**
   * 构建foreach语句
   */
  private void _foreach(String line) {
    line = line.replace(FOREACH, "for(Object").concat("").concat("){");
    builder.append(line);
    foreach = true;
  }

  /**
   * 构建foreach内容
   */
  private void _eachContent(String line) {
    String[] arguments = existArguments(line);
    if (arguments != null && arguments.length != 0) {
      builder.append("Object[] singleArgs = new Object[").append(arguments.length).append("];");
      int i = 0;
      for (String argument : arguments) {
        builder.append("singleArgs[").append(i).append("] = ").append(argument).append(";");
        i++;
      }
      builder.append("arguments.add(singleArgs);");
    }
  }

  /**
   * 结束
   */
  private void _end() {
    if (foreach) {
      foreach = false;
    }
    builder.append("}");
  }

  /**
   * 参数检查
   */
  private String _check(String line) {
    // 如果参数存在逗号需要特殊处理
    if (line.contains(",")) {
      line = line.replaceAll(",", ", ");
    }
    return line;
  }

  /**
   * sql解析
   */
  private void _sqlparse(String line) {
    String sql = line.replaceAll("#\\{(.*?)}", "?").trim();
    if (!"?".equals(sql)) {
      builder.append("sql.append(\"").append(sql).append(" ").append("\");");
    }
    String[] arguments = existArguments(line);
    if (arguments != null && arguments.length != 0) {
      for (String argument : arguments) {
        builder.append("arguments.add(").append(argument).append(");");
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
   * @param input 判断当前字符是不是方法或者是对象
   * @return 处理过后的字符串
   */
  private String invokeToAddGet(String input) {
    if (IS_EQUALS_EMPTY.equals(input)) {
      return "AgentStringUtils.isEmpty";
    }
    if (IS_NOT_EMPTY.equals(input)) {
      return "AgentStringUtils.isNotEmpty";
    }
    if (input.contains(".")) {
      String[] idens = input.split("\\.");
      String objectName = idens[0];
      for (int j = 1; j < idens.length; j++) {
        if (j > 1) {
          input = "Fields.getValue(".concat(input).concat(",\"").concat(idens[j]).concat("\")");
        } else {
          input = "Fields.getValue(".concat(objectName).concat(",\"").concat(idens[j]).concat("\")");
        }
      }
    }
    return input;
  }

  /**
   * 是否存在参数
   *
   * @param input 传入类似"#{xxx}"这样的字符串, 截取出xxx
   * @return 截取到的参数名称
   */
  @SuppressWarnings({"rawtypes"})
  private String[] existArguments(String input) {
    return Matches.find(input, "#\\{(.*?)}\\S*", new Closure(null) {
      @Override
      public Object call(Object... args) {
        String args0 = (String) args[0];
        args0 = args0.replaceAll("#", "");
        if (args0.contains(".")) {
          args0 = invokeToAddGet(args0);
        }
        return args0;
      }
    });
  }

  /**
   * 构建结束
   */
  protected void buildEnd() {
    if (LOG.isDebugEnabled()) {
      LOG.debug(mtClass.toString());
    }
    Class<?> clazz = Objects.requireNonNull(JavaCompiler.compile(classname, mtClass.toString()));
    instance = ClassUtils.newInstance(clazz);
    // 清空springbuilder
    builder = null;
  }

}
