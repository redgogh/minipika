package org.jiakesimk.minipika.components.configuration.node;

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

import lombok.Getter;
import lombok.Setter;
import org.jdom2.Element;
import org.jiakesimk.minipika.framework.factory.Factorys;

/**
 * @author tiansheng
 */
@Getter
@Setter
public class MinipikaXMLConfig implements ElementParser {

  public static final String CHIlD_DATASOURCE       = "datasource";
  public static final String CHIlD_PROPERTIES       = "properties";
  public static final String CHILD_CONFIGURATION    = "configuration";

  /**
   * 所有配置的属性值
   */
  protected PropertiesNode   propertiesNode;

  /**
   * 配置的数据源
   */
  protected DataSourceNode dataSourceNode;

  @Override
  public void parse(Element element) {
    // 解析properties节点
    Element properties = element.getChild(CHIlD_PROPERTIES);
    propertiesNode = Factorys.forClass(PropertiesNode.class);
    if(properties != null) {
      propertiesNode.parse(properties);
    }
    // 解析configuration节点
    Element configuration = element.getChild(CHILD_CONFIGURATION);
    if(configuration != null) {
      propertiesNode.parse(configuration);
    }
    // 解析datasource节点
    Element datasource = element.getChild(CHIlD_DATASOURCE);
    if(datasource != null) {
      dataSourceNode = Factorys.forClass(DataSourceNode.class);
      dataSourceNode.parse(datasource);
    }
  }

}
