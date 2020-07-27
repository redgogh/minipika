package org.jiakesimk.minipika.framework.common;

/* ************************************************************************
 *
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
 *
 * ************************************************************************/

/*
 * Creates on 2020/6/22.
 */

import javassist.ClassPool;
import org.jiakesimk.minipika.components.annotation.Batch;
import org.jiakesimk.minipika.components.annotation.Insert;
import org.jiakesimk.minipika.components.annotation.QueryOf;
import org.jiakesimk.minipika.components.annotation.Update;

import java.lang.annotation.Annotation;

/**
 * 一些通用的常量定义
 *
 * @author tiansheng
 * @email jiakesiws@gmail.com
 */
public interface ConstVariable {

  ClassPool CLASS_POOL                        = new ClassPool();

  Class<? extends Annotation> A_BATCH         = Batch.class;
  Class<? extends Annotation> A_UPDATE        = Update.class;
  Class<? extends Annotation> A_INSERT        = Insert.class;
  Class<? extends Annotation> A_QUERY_OF      = QueryOf.class;

}
