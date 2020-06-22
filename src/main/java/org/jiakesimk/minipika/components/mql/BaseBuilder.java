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
 * Creates on 2020/6/18.
 */

import javassist.NotFoundException;
import lombok.Getter;
import org.jiakesimk.minipika.framework.util.Lists;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 构建与创建基本代码和实体的父类
 *
 * @author tiansheng
 */
public class BaseBuilder {

  /**
   * 预编译代码
   */
  protected StringBuilder precompile = new StringBuilder();

  /**
   * 方法列表
   */
  protected List<MethodBuilder> methods = Lists.newArrayList();

  /**
   * 全类名
   */
  protected String fullname;

  /**
   * 需要导入的包
   */
  private String[] packages = {
          "import java.lang.*;",
          "import java.util.*;",
          "import org.jiakesimk.minipika.framework.util.*;",
  };

  public BaseBuilder(String classname) {
    classname = "$".concat(classname);
    fullname = "org.jiakesimk.minipika.weak.".concat(classname);
    precompile.append("package ").append(fullname).append(";");
    for (String p : packages) {
      precompile.append(p);
    }
    precompile.append("public class ").append(classname).append("{");
  }

  public void createMethod(Method method) throws NotFoundException {
    MethodBuilder builder = new MethodBuilder(method);
    precompile.append(builder.toString());
  }

  /**
   * 调用此方法时代表构建已经完成
   */
  public void carryout() {
    precompile.append("}");
    System.out.println(precompile.toString());
  }

}
