package org.jiakesiws.minipika.components.jdbc.datasource;

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
 * Creates on 2020/6/6.
 */

import org.jiakesiws.minipika.framework.exception.MinipikaException;
import org.jiakesiws.minipika.components.logging.Log;
import org.jiakesiws.minipika.components.logging.LogFactory;
import org.jiakesiws.minipika.framework.utils.AutoClose;
import org.jiakesiws.minipika.framework.utils.Maps;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * 数据源管理器
 *
 * @author 2B键盘
 * @email jiakesiws@gmail.com
 */
public class DataSourceManager
{

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
  public synchronized static void registerDataSource(String name, DataSource dataSource)
  {
    Connection connection = null;
    try
    {
      if (dataSource == null)
      {
        LOG.error("Error DataSource register fail. Cause: DataSource is null.");
        throw new NullPointerException("Error DataSource register fail. Cause: DataSource is null.");
      }
      connection = dataSource.getConnection(); // 测试数据源是否可用
      dataSourceMap.put(name, dataSource);
    } catch (SQLException e)
    {
      LOG.error("Error DataSource register fail. Cause: cannot get conenction.");
      e.printStackTrace();
    } finally
    {
      AutoClose.close(connection); // 关闭连接
    }
  }

  /**
   * @return 当前正在使用的数据源
   */
  public synchronized static DataSource getDataSource()
  {
    return currentDataSource;
  }

  /**
   * 通过名称获取
   */
  public synchronized static DataSource getDataSource(String name)
  {
    return dataSourceMap.get(name);
  }

  /**
   * 获取主数据源
   */
  public synchronized static DataSource getMasterDataSource()
  {
    return dataSourceMap.get(MASTER);
  }

  /**
   * 数据源切换
   *
   * @param name 注册时的数据源名称
   */
  public synchronized static void select(String name)
  {
    DataSource dataSource = dataSourceMap.get(name);
    if (dataSource != null)
    {
      currentDataSource = dataSource;
    } else
    {
      throw new MinipikaException("DataSource select failure. Cause: no " + name + " the DataSource.");
    }
  }

}
