package org.jiakesimk.minipika.framework.common;

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
 * Creates on 2020/6/1.
 */

import org.jiakesimk.minipika.framework.factory.Factorys;

import java.lang.reflect.InvocationHandler;

/**
 * 如果实现了这个接口的话, 那么在调用{@link Factorys#forClass}创建对象的时候
 * 会使用{@link #getProxyHandler}返回的对象.
 *
 * @author tiansheng
 * @email jiakesiws@gmail.com
 */
public interface ProxyHandler extends InvocationHandler {

  /**
   * 获取代理对象
   *
   * @return 代理对象实现
   */
  <T> T getProxyHandler();

}
