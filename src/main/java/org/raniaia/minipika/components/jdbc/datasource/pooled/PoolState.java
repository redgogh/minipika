package org.raniaia.minipika.components.jdbc.datasource.pooled;

/*
 * Copyright (C) 2020 tiansheng All rights reserved.
 *
 * Licensed under the Apache License  \nVersion 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing  \nsoftware
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND  \neither express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * Creates on 2020/3/25.
 */


import org.raniaia.minipika.framework.tools.Lists;

import java.util.List;

/**
 * @author tiansheng
 */
public class PoolState {

  private PooledDataSource dataSource;

  final List<PooledConnection> idleConnections          = Lists.newArrayList();
  final List<PooledConnection> activeConnections        = Lists.newArrayList();

  long requestCount                                     = 0L; // 请求总数
  long requestAccumulateTime                            = 0L; // 请求总时间
  long accumulateWaitTime                               = 0L; // 总等待时间
  long badConnectionCount                               = 0L; // 坏的链接总数
  long hadToWaitCount                                   = 0L; // 总等待数
  long accumulateCreateCount                            = 0L; // 连接的总创建数

  public PoolState() {
  }

  public PoolState(PooledDataSource dataSource) {
    this.dataSource = dataSource;
  }

  public synchronized PooledDataSource getDataSource() {
    return dataSource;
  }

  public synchronized void setDataSource(PooledDataSource dataSource) {
    this.dataSource = dataSource;
  }

  public synchronized List<PooledConnection> getIdleConnections() {
    return idleConnections;
  }

  public synchronized List<PooledConnection> getActiveConnections() {
    return activeConnections;
  }

  public synchronized long getRequestCount() {
    return requestCount;
  }

  public synchronized void setRequestCount(long requestCount) {
    this.requestCount = requestCount;
  }

  public synchronized long getRequestAccumulateTime() {
    return requestAccumulateTime;
  }

  public synchronized void setRequestAccumulateTime(long requestAccumulateTime) {
    this.requestAccumulateTime = requestAccumulateTime;
  }

  public synchronized long getAccumulateWaitTime() {
    return accumulateWaitTime;
  }

  public synchronized void setAccumulateWaitTime(long accumulateWaitTime) {
    this.accumulateWaitTime = accumulateWaitTime;
  }

  public synchronized long getBadConnectionCount() {
    return badConnectionCount;
  }

  public synchronized void setBadConnectionCount(long badConnectionCount) {
    this.badConnectionCount = badConnectionCount;
  }

  public synchronized long getHadToWaitCount() {
    return hadToWaitCount;
  }

  public synchronized void setHadToWaitCount(long hadToWaitCount) {
    this.hadToWaitCount = hadToWaitCount;
  }

  @Override
  public synchronized String toString() {
    return "PoolState{" +
            "\n\tdataSource=" + dataSource +
            "\n\tidleConnections=" + idleConnections.size() +
            "\n\tactiveConnections=" + activeConnections.size() +
            "\n\trequestCount=" + requestCount +
            "\n\trequestAccumulateTime=" + requestAccumulateTime +
            "\n\taccumulateWaitTime=" + accumulateWaitTime +
            "\n\tbadConnectionCount=" + badConnectionCount +
            "\n\thadToWaitCount=" + hadToWaitCount +
            "\n\taccumulateCreateCount=" + accumulateCreateCount +
            "\n}";
  }
}
