package org.jiakesiws.minipika.components.wrapper;

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
 * Creates on 2019/11/13.
 */

import java.util.List;

/**
 * @author lts
 */
public interface BaseMapper<Entity>
{

  /**
   * 根据ID查询一条数据
   *
   * @param id id
   */
  Entity selectById(String id);

  /**
   * 根据查询条件查询单个对象
   *
   * @param wrapper 查询条件封装
   * @return 查询结果
   */
  Entity select(QueryWrapper<Entity> wrapper);

  /**
   * 根据查询条件查询多个对象
   *
   * @param wrapper 查询条件封装
   * @return 查询结果集合
   */
  List<Entity> selectList(QueryWrapper<Entity> wrapper);

  /**
   * 保存单个对象
   *
   * @param Entity 实体对象
   * @return 主键
   */
  String save(Entity Entity);

  /**
   * 批量保存多个对象
   *
   * @param entities 实体对象列表
   * @return 是否执行成功
   */
  boolean saveBatch(List<Entity> entities);

  /**
   * 根据更新条件封装器去进行数据更新
   *
   * @param wrapper 更新条件封装
   * @return 影响行数
   */
  int update(UpdateWrapper<Entity> wrapper);

  /**
   * 根据ID删除一条数据
   *
   * @param id id
   * @return 影响行数
   */
  int delete(String id);

  /**
   * 根据更新条件删除数据
   *
   * @param wrapper 条件封装
   * @return 影响行数
   */
  int delete(UpdateWrapper<Entity> wrapper);

}