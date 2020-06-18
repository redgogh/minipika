package org.jiakesimk.minipika.framework.util;

/*
 * Creates on 2020/3/21.
 */

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 注解工具类
 *
 * @author lts
 */
public class Annotations {

  @SuppressWarnings("unchecked")
  public static <A> A isAnnotation(AccessibleObject target,
                                   Class<? extends Annotation> annotation) {
    if (target.isAnnotationPresent(annotation)) {
      return (A) target.getDeclaredAnnotation(annotation);
    }
    return null;
  }

  @SuppressWarnings("unchecked")
  public static <A> A isAnnotation(Class<?> target,
                                   Class<? extends Annotation> annotation) {
    if (target.isAnnotationPresent(annotation)) {
      return (A) target.getDeclaredAnnotation(annotation);
    }
    return null;
  }

  public static Object getValue(Annotation annotation) throws Exception {
    Method method = annotation.getClass().getDeclaredMethod("value");
    return method.invoke(annotation);
  }

}
