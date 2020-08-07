package org.jiakesimk.minipika.framework.common;

/* ************************************************************************
 *
 * Copyright (C) 2020 2B键盘 All rights reserved.
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
 *
 * ************************************************************************/

/*
 * Creates on 2020/6/22.
 */

import groovy.lang.GroovyClassLoader;
import javassist.ClassPool;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.jiakesimk.minipika.components.annotation.Batch;
import org.jiakesimk.minipika.components.annotation.Insert;
import org.jiakesimk.minipika.components.annotation.QueryOf;
import org.jiakesimk.minipika.components.annotation.Update;

import java.lang.annotation.Annotation;

/**
 * 一些通用的常量定义
 *
 * @author 2B键盘
 * @email jiakesiws@gmail.com
 */
public class ConstVariable
{

  public static final ClassPool CLASS_POOL = new ClassPool();
  public static final Class<? extends Annotation> A_BATCH = Batch.class;
  public static final Class<? extends Annotation> A_UPDATE = Update.class;
  public static final Class<? extends Annotation> A_INSERT = Insert.class;
  public static final Class<? extends Annotation> A_QUERY_OF = QueryOf.class;

  /**
   * groovy类加载器
   */
  public static final GroovyClassLoader groovyClassLoader = ((Callback<GroovyClassLoader>) o ->
  {
    CompilerConfiguration configuration = new CompilerConfiguration();
    configuration.setParameters(true);
    return new GroovyClassLoader(Thread.currentThread().getContextClassLoader(), configuration);
  }).accept(null);

}
