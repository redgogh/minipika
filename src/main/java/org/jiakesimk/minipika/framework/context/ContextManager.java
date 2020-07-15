package org.jiakesimk.minipika.framework.context;

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
 * Creates on 2020/7/2.
 */

import org.jiakesimk.minipika.framework.strategy.FindStrategy;
import org.jiakesimk.minipika.components.configuration.XMLConfigBuilder;

/**
 * 上下文管理
 *
 * @author tiansheng
 */
public class ContextManager {

  /**
   * 上下文是否已经加载
   */
  private static boolean load;

  public static void loadContext() {
    if(!load) {
      XMLConfigBuilder configBuilder = new XMLConfigBuilder();
      configBuilder.load(FindStrategy.getConfigInputStream());
      load = true;
    }
  }

  public static boolean isLoad() {
    return load;
  }

}
