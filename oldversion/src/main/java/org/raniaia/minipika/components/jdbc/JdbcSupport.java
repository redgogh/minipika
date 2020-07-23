package org.jiakesimk.minipika.components.jdbc;

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
 * Creates on 2019/11/11.
 */

import org.jiakesimk.minipika.components.entity.database.ColumnEntity;

import java.util.List;
import java.util.Set;

/**
 * 增删改查操作
 *
 * @author tiansheng
 * @email jiakesiws@gmail.com
 */
public interface JdbcSupport {

  /**
   * 查询并返回对象
   *
   * @param sql  sql语句
   * @param obj  需要返回的对象class
   * @param args 参数
   * @param <T>
   * @return
   */
  <T> T queryForObject(String sql, Class<T> obj, Object... args);

  /**
   * 查询多个结果
   *
   * @param sql  sql语句
   * @param obj  需要返回的对象
   * @param args 参数列表
   * @param <T>
   * @return 封装好的结果集
   */
  <T> List<T> queryForList(String sql, Class<T> obj, Object... args);

  /**
   * 查询多个结果
   *
   * @param sql  sql语句
   * @param obj  需要返回的对象
   * @param args 参数列表
   * @param <T>
   * @return Set集合
   */
  <T> Set<T> queryForSet(String sql, Class<T> obj, Object... args);

  /**
   * 查询并返回JSON字符串
   *
   * @param sql
   * @param args
   * @return
   */
  String queryForJson(String sql, Object... args);

  /**
   * 分页查询,SQL不用加limit
   *
   * @param sql
   * @param args
   * @return
   */
  NativePageHelper queryForPage(String sql, NativePageHelper pageVo, Object... args);

  /**
   * 查询并返回结果集
   *
   * @param sql
   * @param args
   * @return
   */
  NativeResult queryForResult(String sql, Object... args);

  /**
   * 更新所有实体类中的所有数据，但不包括为空的数据。
   *
   * @param obj 实体类
   * @return 更新条数
   */
  int update(Object obj);

  /**
   * 通过SQL语句来更新数据。
   *
   * @param sql  sql语句
   * @param args 参数列表
   * @return 更新条数
   */
  int update(String sql, Object... args);

  /**
   * 传入String
   *
   * @param sql
   * @return
   */
  int updateByString(String sql);

  /**
   * 传入一个实体类，将实体类中为空的数据也进行更新。
   *
   * @param obj 实体类
   * @return 更新条数
   */
  int updateDoNULL(Object obj);

  /**
   * 通过sql语句插入一条数据
   *
   * @param sql  sql语句
   * @param args 参数列表
   * @return 更新条数
   */
  int insert(String sql, Object... args);

  /**
   * 通过实体类来更新数据
   *
   * @param entity 实体类
   * @return 更新条数
   */
  int insert(Object entity);

  /**
   * 批量插入
   *
   * @param entitys
   * @return
   */
  int[] insert(List<Object> entitys);

  /**
   * 统计单张表的所有数据
   *
   * @param target 传入UserEntity
   * @return
   */
  int count(Class<?> target);

  /**
   * 统计sql查询到的所有数据
   *
   * @param sql
   * @return
   */
  int count(String sql, Object... args);

  /**
   * 执行sql
   *
   * @param sql
   * @param args
   * @return
   */
  boolean execute(String sql, Object... args);

  /**
   * 执行执行，传入List
   *
   * @param sql
   * @param args
   * @return
   */
  int[] executeBatch(String sql, List<Object[]> args);

  /**
   * 批量执行，传入Object数组
   *
   * @param sql
   * @param args
   * @return
   */
  int[] executeBatch(String sql, Object[] args);

  /**
   * 多条sql语句批量执行
   *
   * @param sql
   * @param args
   * @return
   */
  int[] executeBatch(String[] sql, List<Object[]> args);

  /**
   * 查询某张表所有字段
   *
   * @param tableName
   */
  List<String> getColumns(String tableName);

  /**
   * 查询表中字段的元数据
   */
  List<ColumnEntity> getColumnMetadata(String table);

}
