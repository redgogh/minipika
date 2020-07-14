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
 * Creates on 2020/6/23.
 */

import org.jiakesimk.minipika.framework.util.Methods;
import org.jiakesimk.minipika.framework.util.Objects;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author tiansheng
 */
public class Invoker {

  protected Object instance;

  /**
   * 执行方法
   * @param method 方法名称
   * @param arguments 方法参数
   * @return 返回值, 返回通用Object
   */
  public Object[] invoke(Method method, Object... arguments) throws Exception {
    return (Object[]) Objects.invoke(method, instance, arguments);
  }

}
