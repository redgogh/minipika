package org.raniaia.minipika.framework.configuration.node;

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

import java.util.List;

/**
 * 数据源节点下的数据
 *
 * @author tiansheng
 */
@Getter
@Setter
public class DataSourceNode implements ElementParser{

  private List<SingleDataSourceNode> dataSourceNodes;

  @Override
  public void parse(Element element) {

  }
}
