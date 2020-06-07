package org.raniaia.minipika.components.jdbc.datasource;

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
 * Creates on 2020/6/6.
 */

import org.raniaia.minipika.framework.exception.MinipikaException;
import org.raniaia.minipika.framework.logging.Log;
import org.raniaia.minipika.framework.logging.LogFactory;
import org.raniaia.minipika.framework.util.Maps;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 数据源管理器
 *
 * @author tiansheng
 */
public class DataSourceManager {

  public static final String MASTER = "master";

  private static final Map<String, DataSource> dataSourceMap = Maps.newConcurrentHashMap();

  private static DataSource currentDataSource;

  private static final Log LOG = LogFactory.getLog(DataSourceManager.class);

  /**
   * 注册数据源
   *
   * @param name       数据源名称
   * @param dataSource 数据源对象
   */
  public synchronized static void registerDataSource(String name, DataSource dataSource) {
    dataSourceMap.put(name, dataSource);
  }

  /**
   * @return 当前正在使用的数据源
   */
  public synchronized static DataSource getDataSource() {
    return currentDataSource;
  }

  /**
   * 获取主数据源
   */
  public synchronized static DataSource getMasterDataSource() {
    return dataSourceMap.get(MASTER);
  }

  /**
   * 数据源切换
   *
   * @param name 注册时的数据源名称
   */
  public synchronized static void select(String name) {
    DataSource dataSource = dataSourceMap.get(name);
    if (dataSource != null) {
      currentDataSource = dataSource;
    } else {
      throw new MinipikaException("DataSource select failure. Cause: no " + name + " the datasource.");
    }
  }

}
