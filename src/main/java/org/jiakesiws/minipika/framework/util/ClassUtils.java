package org.jiakesiws.minipika.framework.util;

/*
 * Creates on 2020/3/21.
 */

import lombok.SneakyThrows;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * 类的静态工具类
 *
 * @author lts
 */
public class ClassUtils {

  /**
   * 创建一个实例。
   */
  @SneakyThrows
  public static Object newInstance(Class<?> clazz) {
    Constructor<?> constructor = clazz.getDeclaredConstructor();
    return constructor.newInstance();
  }

  /**
   * 创建一个实例并传递构造函数参数。
   *
   * @param parametersType  构造函数参数类型。
   * @param parametersValue 构造函数参数值。
   */
  @SneakyThrows
  public static Object newInstance(Class<?> clazz, Class<?>[] parametersType,
                                   Object... parametersValue) {
    Constructor<?> constructor = clazz.getConstructor(parametersType);
    return constructor.newInstance(parametersValue);
  }

  /**
   * 获取所有父类
   */
  public static List<Class<?>> getSuperClasses(Class<?> target) {
    List<Class<?>> classes = Lists.newArrayList();
    getSuperClasses(target, classes);
    return classes;
  }

  /**
   * 获取所有父类递归方法
   */
  static void getSuperClasses(Class<?> target, List<Class<?>> classes) {
    Class<?> superclass = target.getSuperclass();
    if (superclass == null) return;
    classes.add(superclass);
    getSuperClasses(superclass, classes);
  }

  /**
   * 判断是不是接口
   */
  public static boolean isInterface(Class<?> target) {
    return target != null && target.isInterface();
  }

}
