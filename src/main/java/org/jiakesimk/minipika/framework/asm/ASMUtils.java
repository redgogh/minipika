package org.jiakesimk.minipika.framework.asm;

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
 * Creates on 2020/6/1.
 */

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * ASM字节码操作框架工具类
 *
 * @author tiansheng
 */
public class ASMUtils {

  /**
   * 获取{@link Method}的参数{@link Parameter}数组, 其中会获取
   * 真实名称
   *
   * @param method 方法对象
   * @return {@code Parameter}数组
   */
  public static String[] getParameters(Method method) throws IOException {
    return null;
  }

}
