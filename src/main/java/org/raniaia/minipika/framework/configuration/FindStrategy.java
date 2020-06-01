package org.raniaia.minipika.framework.configuration;

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

import org.raniaia.minipika.framework.util.Threads;

import java.io.InputStream;

/**
 * 配置文件寻找策略
 *
 * @author tiansheng
 */
public class FindStrategy {

  // 默认配置文件名称
  static final String DEFAULT_NAME = "minipika.xml";

  private InputStream configInputStream;

  public FindStrategy() {
    tryFindConfig(this::tryFindConfigFromClassPath);
  }

  private void tryFindConfig(Runnable runnable) {
    try {
      runnable.run();
    } catch (Exception ignoreException) {
      // 忽略异常
    }
  }

  /**
   * 从{@code classpath}中寻找配置文件
   */
  private void tryFindConfigFromClassPath() {
    this.configInputStream = Threads.getCallerLoader().getResourceAsStream(DEFAULT_NAME);
  }

}
