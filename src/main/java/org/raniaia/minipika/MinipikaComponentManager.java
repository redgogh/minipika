package org.raniaia.minipika;

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
 * @author tiansheng
 */
public interface MinipikaComponentManager {

  /**
   * 创建一个新的实例对象，并像对象内部的成员进行注入
   *
   * @param clazz 需要实例化的对象类型
   * @param <T>   任意类
   * @return 已注入完成的实例对象
   */
  <T> T newInstance(Class<?> clazz);

  /**
   * 重新加载对象
   *
   * @param object 已实例化后的对象
   */
  void reloading(Object object);

  /**
   * 注册一个已经实例化后的对象到组件管理器
   *
   * @param object 已实例化的对象
   */
  void registerObjectToComponentManager(Object object);

  /**
   * 从组件管理器中删除掉一个对象
   *
   * @param type 对象的类型
   */
  void removeObjectFromComponentManager(Class<?> type);

}
