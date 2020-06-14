package org.jiakesiws.minipika.framework.util;

/*
 * Copyright (C) 2020 lts All rights reserved.
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
 * Creates on 2020/3/11.
 */


import com.alibaba.fastjson.JSON;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;

/**
 * 静态的Set工具类
 *
 * @author lts
 */
public final class Sets {

  /**
   * 创建一个新的且空的{@code HashSet}实例
   */
  public static <E> HashSet<E> newHashSet() {
    return new HashSet<>();
  }

  /**
   * 创建一个可变的 {@code HashSet}实例
   * 拷贝{@link Collection}中的数据到新的{@code HashSet}中。
   */
  public static <E> HashSet<E> newHashSet(Collection<? extends E> collection) {
    return new HashSet<>(collection);
  }

  /**
   * 创建一个新的{@code LinkedHashSet}实例
   */
  public static <E> LinkedHashSet<E> newLinkedHashSet() {
    return new LinkedHashSet<>();
  }

  /**
   * 创建一个新的{@code LinkedHashSet}实例
   * 拷贝{@link Collection}中的数据到新的{@code LinkedHashSet}中。
   */
  public static <E> LinkedHashSet<E> newLinkedHashSet(Collection<? extends E> collection) {
    return new LinkedHashSet<>(collection);
  }

  public static String toString(Collection<?> list) {
    return JSON.toJSONString(list);
  }

}
