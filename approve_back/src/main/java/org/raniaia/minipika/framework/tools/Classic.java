package org.raniaia.minipika.framework.tools;

/*
 * Creates on 2020/3/21.
 */

import org.raniaia.minipika.framework.tools.Lists;
import lombok.SneakyThrows;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * 类的静态工具类
 *
 * @author lts
 */
public class Classic {

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
    Constructor constructor = clazz.getConstructor(parametersType);
    return constructor.newInstance(parametersValue);
  }

  /**
   * 获取所有父类
   */
  public static List<Class<?>> getSuperClasses(Class<?> classic) {
    List<Class<?>> classes = Lists.newArrayList();
    getSuperClasses(classic, classes);
    return classes;
  }

  /**
   * 获取所有父类递归方法
   */
  static void getSuperClasses(Class<?> classic, List<Class<?>> classes) {
    Class<?> superclass = classic.getSuperclass();
    if (superclass == null) return;
    classes.add(superclass);
    getSuperClasses(superclass, classes);
  }

}
