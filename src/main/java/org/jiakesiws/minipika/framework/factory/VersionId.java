package org.jiakesiws.minipika.framework.factory;

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
 * Creates on 2020/6/1.
 */

import org.jiakesiws.minipika.framework.utils.UniqueId;

/**
 * 实现Minipika插件的VersionId，用于在组件管理器中标识出自己的位置
 *
 * @author 2B键盘
 * @email jiakesiws@gmail.com
 */
public interface VersionId {

  /**
   * @return {@code long}类型的VersionId, 默认使用{@link #getVersionId()}
   *         生成一个唯一的对象标识, 如果有{@code #serialVersionId}的话也可以使用
   *         serialVersionId作为对象的唯一标识。
   *         当然了，对象的hasCode也是一个不错的选择，看自己喜欢。
   */
  default String versionId() {
    return getVersionId() + this.hashCode();
  }

  /**
   * @return 返回一个对象的唯一ID
   */
  static String getVersionId() {
    return UniqueId.getUUID("{2}");
  }

}
