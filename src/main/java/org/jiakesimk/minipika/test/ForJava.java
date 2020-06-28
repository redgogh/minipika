package org.jiakesimk.minipika.test;

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
 * Creates on 2020/6/18.
 */

import groovy.lang.Closure;
import org.jiakesimk.minipika.framework.util.ArrayUtils;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

/**
 * @author tiansheng
 */
public class ForJava {

  @Test
  public void getParameterNamesTest() throws NoSuchMethodException {
    Method method = MqlMapper.class.getDeclaredMethod("addBatch", List.class);
    for(Parameter parameter : method.getParameters()) {
      System.out.println(parameter.getName());
    }
  }

}
