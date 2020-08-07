package org.jiakesimk.minipika.components.annotation;

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
 * Creates on 2020/7/5.
 */

/**
 * @author 2B键盘
 * @email jiakesiws@gmail.com
 */
public enum QueryMode {

  /**
   * NULL
   */
  NULL,

  /**
   * 查询单个对象
   */
  FOR_OBJECT,

  /**
   * 查询集合对象
   */
  FOR_LIST,

  /**
   * 查询结果集返回Map
   */
  FOR_MAP,

  /**
   * 查询结果集返回JSON
   */
  FOR_JSON

}
