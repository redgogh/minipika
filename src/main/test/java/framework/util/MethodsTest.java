package framework.util;

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

import groovy.MqlMapper;
import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.jiakesimk.minipika.framework.util.ArrayUtils;
import org.jiakesimk.minipika.framework.util.Methods;
import org.junit.Test;

/**
 * @author tiansheng
 */
public class MethodsTest {

  @Test
  public void test() throws NotFoundException {
    ArrayUtils.toString(Methods.getParameterNames(MqlMapper.class.getDeclaredMethods()[0]));
  }

}
