package org.jiakesimk.minipika.components.jdbc;

/*
 * Copyright (C) 2020 Tiansheng All rights reserved.
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
 * Creates on 2019/11/13.
 */

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

/**
 * 封装原生结果集，用于缓存结果从而使ResultSet提前关闭。
 *
 * @author tiansheng
 */
public interface NativeResultSet {

  /**
   * 构建一个{@link NativeResultSet}结果集
   *
   * @param resultSet JDBC查询返回的结果集
   * @return {@link NativeResultSet}结果集实现
   */
  NativeResultSet build(ResultSet resultSet);

  /**
   * 将结果集转换成Java对象
   *
   * @param target 转换的目标对象类类型
   * @return 对象实例
   */
  <T> T conversionJavaBean(Class<T> target);

  /**
   * 将结果集转换成Java集合
   *
   * @param target 集合目标对象类型
   * @return {@code List}实例
   */
  <T> List<T> conversionJavaList(Class<T> target);

  /**
   * 选择结果集中的下一条数据
   */
  void hasNext();

  /**
   * 当前被选中的数据
   */
  String next();

  /**
   * 重新设置索引
   */
  void reset();

  /**
   * 将结果集转换成JSON字符串
   *
   * @return json字符串
   */
  String toJSONString();

}
