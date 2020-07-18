package org.jiakesimk.minipika.components.jdbc.datasource.pooled;

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

import org.jiakesimk.minipika.framework.PropertyNames;
import org.jiakesimk.minipika.framework.strategy.ThreadNotifyStrategy;
import org.jiakesimk.minipika.framework.utils.Lists;
import org.jiakesimk.minipika.framework.utils.Maps;
import org.jiakesimk.minipika.framework.utils.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author tiansheng
 */
public class PooledState implements ThreadNotifyStrategy {

  private PooledDataSource pooledDataSource;

  /**
   * sql执行次数统计
   */
  protected Map<Integer, Long> frequency                        = Maps.newConcurrentHashMap();

  /**
   * sql执行耗时
   */
  protected Map<Integer, Long> sqlTimeConsuming                 = Maps.newConcurrentHashMap();

  protected Map<Integer, String> sqlKeyCollect                  = Maps.newConcurrentHashMap();

  protected List<PooledConnection> idleConnections              = Lists.newArrayList();

  protected List<PooledConnection> activeConnections            = Lists.newArrayList();

  protected long requestCount                                   = 0L;   // 链接请求次数统计
  protected long badConnectionCount                             = 0L;   // 无效链接总数
  protected long requestAccumulateTime                          = 0L;   // 链接请求时间统计
  protected long hadToWaitCount                                 = 0L;   // 当前有多少个链接在等待中
  protected long accumulateCreatesCount                         = 0L;   // 链接创建统计
  protected long maximumConnections                             = 10L;  // 最大连接数
  protected long minimumConnections                             = 5L;   // 最小连接数
  protected long currentConnectionsCount                        = 0L;   // 当前总连接数
  protected long maximumWaitTimeout                             = 0L;   // 链接最长等待时间
  protected long maximumWaitTimeoutCount                        = 0L;   // 链接等待时间统计

  public PooledState() {
  }

  public PooledState(PooledDataSource dataSource) {
    {
      this.pooledDataSource = dataSource;
    }
    {
      // 最大连接数
      long max = StringUtils.asLong(System.getProperty(PropertyNames.MAX_CONNECTION));
      if (max != 0L) {
        this.maximumConnections = max;
      }
      // 最小连接数
      long min = StringUtils.asLong(System.getProperty(PropertyNames.MIN_CONNECTION));
      if (min != 0L) {
        this.minimumConnections = min;
      }
    }
  }

  public void setSqlTimeConsuming(String sql, Long time) {
    Integer key = getKey(sql);
    sqlTimeConsuming.put(key, time);
  }

  /**
   * 执行次数+1
   */
  public void addFrequency(String sql) {
    Integer key = getKey(sql);
    Long frequ = frequency.get(key);
    if(frequ == null) {
      frequency.put(key, 1L);
    } else {
      frequency.put(key, (frequ + 1));
    }
  }

  private Integer getKey(String sql) {
    sql = sql.trim();
    Integer key = (sql.charAt(0) + sql.length()) + sql.hashCode();
    if(!sqlKeyCollect.containsKey(key)) {
      sqlKeyCollect.put(key, sql);
    }
    return key;
  }

  @Override
  public void notifyAllStrategy() {
    this.notifyAll();
  }

  public synchronized long getRequestCount() {
    return this.requestCount;
  }

  public synchronized long getRequestAccumulateTime() {
    return this.requestAccumulateTime;
  }

  public synchronized long getBadConnectionCount() {
    return this.badConnectionCount;
  }

  public synchronized long getHadToWaitCount() {
    return this.hadToWaitCount;
  }

  public synchronized long getAccumulateCreatesCount() {
    return this.accumulateCreatesCount;
  }

}
