package org.jiakesimk.minipika.components.wrapper

import static org.jiakesimk.minipika.framework.utils.Lists.newArrayList

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
 * Creates on 2020/7/21.
 */

/**
 * @author lts
 */
class AbstractWrapper<T> {

  /** sql语句 **/
  private def sqlBuilder = new StringBuilder()

  /** 参数列表 **/
  private def arguments = newArrayList()

  protected void append(String paragraphs) {
    sqlBuilder.append(paragraphs)
  }

  protected void append(String paragraphs, argument) {
    sqlBuilder.append(paragraphs)
    arguments.add(argument)
  }

}
