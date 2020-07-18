package org.jiakesimk.minipika.framework.utils;

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
 * Creates on 2019/11/13.
 */

import java.lang.reflect.Method;

/**
 * @author lts
 */
public class Objects {

  public static Object invoke(Method method, Object object, Object... args) throws Exception {
    Class<?> clazz = object.getClass();
    Method method0 = clazz.getDeclaredMethod(method.getName(), method.getParameterTypes());
    return method0.invoke(object, args);
  }

  /**
   * 获取对象占用内存大小
   *
   * @param o 对象实例
   * @return 对象大小
   */
  public static native long sizeof(Object o);

}
