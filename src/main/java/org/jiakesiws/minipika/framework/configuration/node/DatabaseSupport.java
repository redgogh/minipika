package org.jiakesiws.minipika.framework.configuration.node;

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

/**
 * 支持的数据库类型
 *
 * @author tiansheng
 */
public enum DatabaseSupport {

  /**
   * 未知数据源类型，默认使用JDBC进行驱动加载
   */
  JDBC,

  /**
   * Oracle旗下的一款开源的关系型数据库
   */
  MYSQL,

  /**
   * Oracle自己的关系型数据库
   */
  ORACLE,

  /**
   * 类似Oracle的数据库叫达梦，由国内的达梦公司维护
   */
  DM,

  /**
   * 微软的一款叼的不行的关系型数据库
   */
  SQLSERVER,

  /**
   * 一款NOSQL数据库
   */
  MANGODB,

}
