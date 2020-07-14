package org.jiakesimk.minipika.framework.util;

/*
 * Creates on 2019/11/13.
 */

import java.lang.reflect.Method;

/**
 * @author lts
 */
public class Objects {

  public static Object invoke(Method method, Object object, Object... args) throws Exception {
    Method method0 = object.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());
    return method0.invoke(object, args);
  }

}
