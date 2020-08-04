package org.jiakesimk.minipika.components.configuration.node;

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

import org.jiakesimk.minipika.components.configuration.wrapper.ElementWrapper;
import org.jiakesimk.minipika.framework.exception.XMLParseException;
import org.jiakesimk.minipika.framework.utils.Lists;

import java.util.List;

/**
 * 数据源节点下的数据
 *
 * @author tiansheng
 * @email jiakesiws@gmail.com
 */
public class DataSourceNode implements ElementParser {

  static final String MASTER = "master";

  private final List<SourceConfig> dataSourceNodes = Lists.newArrayList();

  @Override
  public void parse(ElementWrapper element) {
    // 判断是否存在主数据源
    checkIsExistsMaster(element);
    List<ElementWrapper> elements = element.getChildren();
    for (ElementWrapper datasource : elements) {
      SourceConfig sourceConfig = new SourceConfig();
      sourceConfig.parse(datasource);
      dataSourceNodes.add(sourceConfig);
    }
  }

  /**
   * 检查数据源节点中是否存在主节点
   *
   * @param element 被检查的元素对象
   */
  void checkIsExistsMaster(ElementWrapper element) {
    if (element.getChild(MASTER) == null)
      throw new XMLParseException("minipika config content does not exist master node in DataSource node. " +
              "Cause: master DataSource node is required.");
  }

}
