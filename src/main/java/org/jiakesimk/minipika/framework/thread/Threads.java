package org.jiakesimk.minipika.framework.thread;

/*
 * Creates on 2020/3/24.
 */

/**
 * 静态{@code Thread}工具。
 *
 * @author lts
 */
public class Threads {

  /**
   * 获取方法的调用者。
   */
  public static Class<?> getCaller() {
    Class<?> clazz = null;
    try {
      StackTraceElement[] elements = Thread.currentThread().getStackTrace();
      String classname = elements[elements.length - 1].getClassName();
      clazz = Class.forName(classname);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return clazz;
  }

  /**
   * @return 调用者名称
   */
  public static String getCallerName() {
    return getStackTraceElement().getClassName();
  }

  /**
   * @return 当前线程名称
   */
  public static String getCurrentThreadName() {
    return Thread.currentThread().getName();
  }

    /**
     * @return 堆栈调用信息
     */
  public static StackTraceElement getStackTraceElement() {
    return getStackTraceElement(3);
  }

  /**
   * @return 第n个堆栈调用信息
   */
  public static StackTraceElement getStackTraceElement(int n) {
    StackTraceElement[] elements = Thread.currentThread().getStackTrace();
    return elements[n];
  }

  /**
   * @return 获取调用者的类加载器
   */
  public static ClassLoader getCallerLoader() {
    return getCaller().getClassLoader();
  }

}
