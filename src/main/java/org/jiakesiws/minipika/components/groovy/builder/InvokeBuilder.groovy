package org.jiakesiws.minipika.components.groovy.builder;

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
 * Creates on 2020/6/22.
 */

import org.jiakesiws.minipika.components.logging.Log;
import org.jiakesiws.minipika.components.logging.LogFactory;
import org.jiakesiws.minipika.framework.compiler.JavaCompiler;
import org.jiakesiws.minipika.framework.utils.ClassUtils;
import org.jiakesiws.minipika.framework.utils.Matches;
import org.jiakesiws.minipika.framework.utils.Methods;
import org.jiakesiws.minipika.framework.utils.StringUtils;

import java.lang.reflect.Method;

/**
 * @author 2B键盘* @email jiakesiws@gmail.com
 */
class InvokeBuilder
{

  private final String classname;

  final StringBuilder mtClass = new StringBuilder();

  private static final Log LOG = LogFactory.getLog(InvokeBuilder.class);

  private static final String IF = "#IF";

  private static final String END = "#END";

  private static final String FOREACH = "#FOREACH";

  private static final String IS_NOT_EMPTY = "NOT_EMPTY";

  private static final String IS_EQUALS_EMPTY = "EMPTY";

  protected Object instance;

  //
  // 用于识别当前是不是解析到foreach语句
  // 如果是的话foreach语句内容需要特殊处理
  //
  private boolean foreach = false;

  //
  // 构建方法的StringBuilder, 最后会将它设置为null
  //
  private StringBuilder builder = new StringBuilder();

  InvokeBuilder(String classname)
  {
    int lastIndexOf = classname.lastIndexOf(".");
    mtClass.append("package ").append(classname, 0, lastIndexOf).append(";");
    mtClass.append("import java.lang.*;");
    mtClass.append("import java.util.*;");
    mtClass.append("import java.lang.*;");
    mtClass.append("import java.math.*;");
    mtClass.append("import org.jiakesiws.minipika.framework.utils.*;");
    mtClass.append("import org.jiakesiws.minipika.framework.utils.agent.*;");
    mtClass.append("@SuppressWarnings(\"unchecked\")");
    mtClass.append("public class ").append(classname, lastIndexOf + 1, classname.length()).append("{}");
    this.classname = classname;
  }

  /**
   * 创建一个方法
   *
   * @param method 方法名
   * @param src 动态sql
   */
  protected void createMethod(Method method, String src)
  {
    buildHead(method, src);
    builder.append("Object[] objects = new Object[2];");
    builder.append("objects[0] = sql.toString();");
    builder.append("objects[1] = arguments;");
    builder.append("return objects;}");
    mtClass.insert(mtClass.length() - 1, builder);
    // -- debug使用
    // System.out.println(mtClass);
  }

  /**
   * 构建方法头
   *
   * @param method 方法元数据对象
   * @param src 动态sql
   */
  private void buildHead(Method method, String src)
  {
    StringUtils.clear(builder);
    String methodName = method.getName(); // 方法名
    String[] paramNames = Objects.requireNonNull(Methods.getParameterNames(method),
            "The real parameters name of method is not obtained."); // 方法参数名
    Class<?>[] paramTypes = method.getParameterTypes();
    // 创建方法声明
    builder.append("public Object[] ").append(methodName).append("(");
    for (int i = 0; i < paramNames.length; i++)
    {
      builder.append(paramTypes[i].getName()).append(" ").append(paramNames[i]);
      builder.append(",");
    }
    if (',' == (builder.charAt(builder.length() - 1)))
    {
      builder.delete(builder.length() - 1, builder.length());
    }
    builder.delete(builder.length(), builder.length()).append("){"); // 方法头部声明结尾
    // 构建方法体
    buildBody(src);
  }

  /**
   * 构建方法体
   *
   * @param src 动态sql
   */
  private void buildBody(String src)
  {
    _buildHead();
    String[] single = src.split("\n");
    for (String line : single)
    {
      line = line.trim();

      if (line.contains(IF))
      {
        _if(line);
        continue;
      }

      if (line.contains(FOREACH))
      {
        _foreach(line);
        continue;
      }

      if (line.contains(END))
      {
        _end();
        continue;
      }

      {
        line = _check(line);
        if (foreach)
        {
          _eachContent(line);
        } else
        {
          _sqlparse(line);
        }
      }
    }
  }

  /**
   * 构建方法所需的成员
   */
  private void _buildHead()
  {
    builder.append("StringBuilder sql = new StringBuilder();")
    builder.append("List arguments = new LinkedList();")
  }

  /**
   * 构建if语句
   */
  private void _if(String line)
  {
    line = line.replace(IF, "").trim()
    line = "".concat("if(").concat(parseIfStatement(line)).concat("){")
    builder.append(line)
  }

  /**
   * 构建foreach语句
   */
  private void _foreach(String line)
  {
    line = line.replace(FOREACH, "for(Object").concat("").concat("){")
    builder.append(line)
    foreach = true
  }

  /**
   * 构建foreach内容
   */
  private void _eachContent(String line)
  {
    String[] arguments = existArguments(line);
    if (arguments != null && arguments.length != 0)
    {
      builder.append("Object[] singleArgs = new Object[").append(arguments.length).append("];");
      int i = 0;
      for (String argument : arguments)
      {
        builder.append("singleArgs[").append(i).append("] = ").append(argument).append(";");
        i++;
      }
      argsAppend("singleArgs");
    }
  }

  /**
   * 结束
   */
  private void _end()
  {
    if (foreach)
    {
      foreach = false;
    }
    builder.append("}");
  }

  /**
   * 参数检查
   */
  private String _check(String line)
  {
    // 如果参数存在逗号需要特殊处理
    if (line.contains(","))
    {
      line = line.replaceAll(",", ", ");
    }
    return line;
  }

  /**
   * sql解析
   */
  private void _sqlparse(String line)
  {
    line = like(line);
    String sql = line.replaceAll("#\\{(.*?)}", "?").trim();
    if (!"?".equals(sql))
    {
      sqlAppend(sql);
    }
    String[] arguments = existArguments(line);
    if (arguments != null && arguments.length != 0)
    {
      for (String argument : arguments)
      {
        argsAppend(argument);
      }
    }
    println ""
  }

  /**
   * 解析if语句
   *
   * @param input if语句块
   */
  private String parseIfStatement(String input)
  {
    input = input.concat(" ");
    char[] charArray = input.toCharArray();
    StringBuilder builder = new StringBuilder();
    StringBuilder temp = new StringBuilder();
    for (char ch : charArray)
    {
      if (('a' <= ch && 'z' >= ch) ||
              ('A' <= ch && 'Z' >= ch) ||
              (ch == '_' || ch == '$' || ch == '.'))
      {
        temp.append(ch);
      } else
      {
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
  private String invokeToAddGet(String input)
  {
    if (IS_EQUALS_EMPTY.equals(input))
    {
      return "AgentStringUtils.isEmpty";
    }
    if (IS_NOT_EMPTY.equals(input))
    {
      return "AgentStringUtils.isNotEmpty";
    }
    if (input.contains("."))
    {
      String[] idens = input.split("\\.");
      String objectName = idens[0];
      for (int j = 1; j < idens.length; j++)
      {
        if (j > 1)
        {
          input = getFieldValue(input, idens[j]);
        } else
        {
          input = getFieldValue(objectName, idens[j]);
        }
      }
    }
    return input;
  }

  /**
   * 获取成员属性值
   *
   * @param object 从object对象中获取
   * @param name 获取name属性的值
   * @return code
   */
  private String getFieldValue(String object, String name)
  {
    return "Fields.getValue(".concat(object).concat(",\"").concat(name).concat("\")");
  }

  /**
   * 添加sql单条语句
   *
   * @param sql SQL语句字符串
   */
  private void sqlAppend(String sql)
  {
    builder.append("sql.append(\"").append(sql).append(" ").append("\");");
  }

  /**
   * 添加当前sql参数
   *
   * @param argument 参数对象
   */
  private void argsAppend(String argument)
  {
    builder.append("arguments.add(").append(argument).append(");");
  }

  /**
   * 是否存在参数
   *
   * @param input 传入类似"#{xxx}"这样的字符串, 截取出xxx
   * @return 截取到的参数名称
   */
  private String[] existArguments(String input)
  {
    if (StringUtils.isNotEmpty(input))
    {
      return Matches.find(input, "#\\{(.*?)}\\S*", value ->
      {
        value = value.replaceAll("#", "");
        if (value.contains("."))
        {
          value = invokeToAddGet(value);
        }
        return value;
      });
    }
    return null;
  }

  /**
   * 构建结束
   */
  protected void over()
  {
    if (LOG.isDebugEnabled())
    {
      LOG.debug(mtClass.toString());
    }
    Class<?> clazz = Objects.requireNonNull(JavaCompiler.compile(classname, mtClass.toString()));
    instance = ClassUtils.newInstance(clazz);
    // 清空SpringBuilder
    builder = null;
  }

  /**
   * 执行方法
   *
   * @param method 方法名称
   * @param arguments 方法参数
   * @return 返回值, 返回通用Object
   */
  protected Object[] invoke(Method method, Object... arguments) throws Exception
  {
    return (Object[]) org.jiakesiws.minipika.framework.utils.Objects.invoke(method, instance, arguments);
  }

  /**
   * like函数
   */
  private static String like(String line)
  {
    if (line.contains("like("))
    {
      line = line.replaceAll("like\\(", "like concat(")
      line = line.replace("(%", "('%'")
              .replace("%)", "'%')")
      Matches.find(line, 'concat\\((.*?)\\)', {
        String[] apost = it.split("'")
        def name = "#{" + apost[2] + "}"
        def finalLike = "'" + apost[1] + "'," +
                name + ",'" + apost[3] + "'"
        line = line.replace(it, finalLike)
      })
    }
    return line;
  }

}
