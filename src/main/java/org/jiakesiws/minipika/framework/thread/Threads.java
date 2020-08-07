package org.jiakesiws.minipika.framework.thread;

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
 * Creates on 2020/3/24.
 */

/**
 * 静态{@code Thread}工具。
 *
 * @author lts
 * @email jiakesiws@gmail.com
 */
public class Threads
{

  /**
   * 获取方法的调用者。
   */
  public static Class<?> getCaller()
  {
    Class<?> clazz = null;
    try
    {
      StackTraceElement[] elements = Thread.currentThread().getStackTrace();
      String classname = elements[3].getClassName();
      clazz = Class.forName(classname);
    } catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    return clazz;
  }

  /**
   * @return 调用者名称
   */
  public static String getCallerName()
  {
    return getStackTraceElement().getClassName();
  }

  /**
   * @return 当前线程名称
   */
  public static String getCurrentThreadName()
  {
    return Thread.currentThread().getName();
  }

  /**
   * @return 堆栈调用信息
   */
  public static StackTraceElement getStackTraceElement()
  {
    return getStackTraceElement(3);
  }

  /**
   * @return 第n个堆栈调用信息
   */
  public static StackTraceElement getStackTraceElement(int n)
  {
    StackTraceElement[] elements = Thread.currentThread().getStackTrace();
    return elements[n];
  }

  /**
   * @return 获取调用者的类加载器
   */
  public static ClassLoader getCallerLoader()
  {
    return getCaller().getClassLoader();
  }

}
