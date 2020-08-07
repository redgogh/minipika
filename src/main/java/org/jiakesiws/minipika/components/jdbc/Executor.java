package org.jiakesiws.minipika.components.jdbc;

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
 * Creates on 2019/11/11.
 */

import java.sql.SQLException;
import java.util.List;

/**
 * 增删改查操作
 *
 * @author 2B键盘
 * @email jiakesiws@gmail.com
 */
public interface Executor
{

  /**
   * 查询并返回对象
   *
   * @param sql  sql语句
   * @param obj  需要返回的对象class
   * @param args 参数
   * @return 封装好的类型对象
   */
  <T> T queryForObject(String sql, Class<T> obj, Object... args) throws SQLException;

  /**
   * 查询多个结果
   *
   * @param sql  sql语句
   * @param obj  需要返回的对象
   * @param args 参数列表
   * @return 封装好的结果集
   */
  <T> List<T> queryForList(String sql, Class<T> obj, Object... args) throws SQLException;

  /**
   * 查询并返回JSON字符串
   *
   * @param sql  sql语句
   * @param args 参数列表
   * @return JSON字符串
   */
  String queryForJson(String sql, Object... args) throws SQLException;

  /**
   * 查询并返回Native结果集{@link NativeResultSet}
   *
   * @param sql  sql语句
   * @param args 参数列表
   * @return 封装的结果集{@link NativeResultSet}对象
   */
  NativeResultSet queryForNativeResult(String sql, Object... args) throws SQLException;

  /**
   * 通过sql语句更新一条数据
   *
   * @param sql  sql语句
   * @param args 参数列表
   * @return 更新条数
   */
  int update(String sql, Object... args) throws SQLException;

  /**
   * 通过sql语句插入一条数据
   *
   * @param sql  sql语句
   * @param args 参数列表
   * @return 更新条数
   */
  int insert(String sql, Object... args) throws SQLException;

  /**
   * 统计sql查询到的所有数据, 使用当前方法的时候请查看说明，
   * 因为这个方法可能会因为SQL不匹配问题导致异常情况的发生，
   * <p>
   * {@code count}方法的执行原理是将sql全部转换成小写，并截取select
   * 字符串到第一个from字符串，如果sql存在必要的大写内容会被转化成小写，
   * 并且如果select中的字段存在from的话可能会导致sql出错，所以使用这个方法
   * 前请查看一下SQL是否符合要求，再使用!
   *
   * @param sql sql语句
   * @return 查询结果总数
   */
  int count(String sql, Object... args) throws SQLException;

  /**
   * 执行一条sql语句
   *
   * @param sql  sql语句
   * @param args 参数列表
   * @return true执行成功， false执行失败
   */
  boolean execute(String sql, Object... args) throws SQLException;

  /**
   * 批量执行
   *
   * @param sql  sql语句
   * @param args 参数集合
   * @return 影响行数
   */
  int[] batch(String sql, List<Object[]> args) throws SQLException;

  /**
   * 批量执行，传入Object数组
   *
   * @param sql  sql语句
   * @param args 参数列表
   * @return 影响行数
   */
  int[] batch(String sql, Object[] args) throws SQLException;

  /**
   * 多条sql语句批量执行
   *
   * @param sql  sql列表
   * @param args 参数列表
   * @return 影响行数
   */
  int[] batch(String[] sql, List<Object[]> args) throws SQLException;

}
