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
 * Creates on 2020/6/18.
 */

import groovy.lang.Closure;
import javassist.NotFoundException;
import org.jiakesimk.minipika.components.annotation.Delete;
import org.jiakesimk.minipika.components.annotation.Insert;
import org.jiakesimk.minipika.components.annotation.Select;
import org.jiakesimk.minipika.components.annotation.Update;
import org.jiakesimk.minipika.framework.util.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author tiansheng
 */
public class MethodBuilder {

  private final StringBuilder builder = new StringBuilder();

  private ExecuteMode mode;

  private Annotation annotation;

  /**
   * 当前方法要执行的是哪一种操作
   */
  public enum ExecuteMode {SELECT, INSERT, UPDATE, DELETE}

  /**
   * 创建一个方法构建器
   *
   * @param method 目标方法对象
   */
  public MethodBuilder(Method method) throws NotFoundException {
    initialization(method);
  }

  /**
   * 当初始化后{@link #builder}中的内容是这样的：<code>
   * public Object[] name(String a, String b)
   * </code>
   * 参数a,b只是举了一个例子，真实的情况下它可能是一个其他实体对象。
   *
   * @param method 目标方法对象
   * @throws NotFoundException
   */
  private void initialization(Method method) throws NotFoundException {
    if (initMode(method) != null) {
      Map<String, String> arguments = Maps.newHashMap();
      String[] names = Methods.getParameterNames(method);
      Class<?>[] types = method.getParameterTypes();
      for (int i = 0; i < types.length; i++) {
        arguments.put(types[i].getName(), names[i]);
      }
      builder.append(StringUtils.format("public Object[] {}", method.getName()));
      builder.append("(");
      for (Map.Entry<String, String> entry : arguments.entrySet()) {
        builder.append(StringUtils.format("{} {},", entry.getKey(), entry.getValue()));
      }
      int length = builder.length();
      builder.delete(length - 1, length).append(")");
      builder.append("{");
      builder.append("Object[] result = new Object[2];"); // 返回值，0是sql执行脚本，1是参数
      builder.append("StringBuilder sql = new StringBuilder();");
      builder.append("List<Object> arguments = new LinkedList();");
      try {
        block((String) Annotations.getValue(annotation));
      } catch (Exception e) {
        e.printStackTrace();
      }
      builder.append("result[0] = sql.toString();");
      builder.append("result[1] = arguments;");
      builder.append("return result;");
      builder.append("}");
    }
  }


  /**
   * 创建代码块
   */
  private void block(String sql) {
    String[] lines = sql.split("\n");
    for (String line : lines) {
      line = line.trim();
      if (StringUtils.isEmpty(line)) continue;
      // 如果存在if标签的话就认定这是一个if语句
      if (line.contains("#if")) {
        line = line.replaceAll("#if", "if(");
        String group[] = existArguments(line);
        for (String s : group) {
          line = line.replaceFirst("#(.*?)\\S+", s);
        }
        line = line.concat("){");
        builder.append(line);
        continue;
      }
      // 如果存在end标签的话就认定这是一个结束语句
      if (line.contains("#end")) {
        builder.append("}");
        continue;
      }
      String group[] = existArguments(line);
      for (String value : group) {
        builder.append("arguments.add(").append(value).append(");\n");
      }
      line = line.replaceAll("#(.*?)\\S+", "?");
      builder.append("sql.append(\"").append(line).append("\");");
    }
  }

  /**
   * 初始化sql脚本执行类型
   */
  private ExecuteMode initMode(Method method) {
    if (method.isAnnotationPresent(Select.class)) {
      this.mode = ExecuteMode.SELECT;
      this.annotation = method.getDeclaredAnnotation(Select.class);
    }
    if (method.isAnnotationPresent(Update.class)) {
      this.mode = ExecuteMode.UPDATE;
      this.annotation = method.getDeclaredAnnotation(Update.class);
    }
    if (method.isAnnotationPresent(Insert.class)) {
      this.mode = ExecuteMode.INSERT;
      this.annotation = method.getDeclaredAnnotation(Insert.class);
    }
    if (method.isAnnotationPresent(Delete.class)) {
      this.mode = ExecuteMode.DELETE;
      this.annotation = method.getDeclaredAnnotation(Delete.class);
    }
    return mode;
  }

  @Override
  public String toString() {
    return builder.toString();
  }

  private String[] existArguments(String input) {
    // 判断字符串中是否存在参数
    return Matches.find(input, "#(.*?)\\S+", new Closure(null) {
      @Override
      public Object call(Object... args) {
        String value = ((String) args[0]).replace("#", "");
        value = value.replaceAll("\\("," ");
        value = value.replaceAll("\\)"," ");
        if (value.contains(".")) {
          String[] d = value.split("\\.");
          StringBuilder v = new StringBuilder(d[0]);
          for (int i = 1; i < d.length; i++) {
            v.append(".get").append(StringUtils.toUpperCase(d[i], 1)).append("()");
          }
          value = v.toString();
        }
        return value;
      }
    });
  }

}
