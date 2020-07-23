package org.jiakesimk.minipika.framework.strategy;

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

import org.jiakesimk.minipika.framework.thread.Threads;
import org.xml.sax.InputSource;

import java.io.InputStream;

/**
 * 配置文件寻找策略
 *
 * @author tiansheng
 * @email jiakesiws@gmail.com
 */
public class FindStrategy {

  // 默认配置文件名称
  static final String DEFAULT_NAME = "minipika.xml";

  private static InputSource systemId;

  private FindStrategy() {
  }

  public static InputSource getConfigInputStream() {
    try {
      if (systemId == null) {
        usingConfigFromClassPath();
      }
      return systemId;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 从{@code classpath}中寻找配置文件
   */
  private static void usingConfigFromClassPath() {
    InputStream stream = Threads.getCallerLoader().getResourceAsStream(DEFAULT_NAME);
    systemId = new InputSource(stream);
  }

}
