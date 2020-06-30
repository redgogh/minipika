package org.jiakesimk.minipika.framework.util;

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

import com.github.houbb.asm.tool.reflection.AsmMethods;
import org.jiakesimk.minipika.framework.exception.MinipikaException;
import org.jiakesimk.minipika.framework.util.Fields;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

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
  public static Parameter[] getParameters(Method method) {
    try {
      List<String> strValue = AsmMethods.getParamNamesByAsm(method);
      Parameter[] parameters = method.getParameters();
      for (int i = 0; i < parameters.length; i++) {
        Fields.set(parameters[i], strValue.get(i), "name");
      }
      return parameters;
    } catch (Exception e) {
      e.printStackTrace();
      throw new MinipikaException(e);
    }
  }

}
