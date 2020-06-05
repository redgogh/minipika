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

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.raniaia.minipika.framework.configuration.node.MinipikaXMLConfig;
import org.raniaia.minipika.framework.factory.Factorys;
import org.raniaia.minipika.framework.logging.Log;
import org.raniaia.minipika.framework.logging.LogFactory;
import org.raniaia.minipika.framework.util.Threads;

/**
 * @author tiansheng
 */
public class XMLConfigBuilder {

  final Log log = LogFactory.getLog(XMLConfigBuilder.class);

  public XMLConfigBuilder(String xfile) {
    try {
      xfile = perfectPath(xfile);
      SAXBuilder builder = Factorys.forClass(SAXBuilder.class);
      Document document = builder.build(xfile);
      Element root = document.getRootElement();
      initialize(root);
    } catch (Exception e) {
      e.printStackTrace();
      log.error("Error xml file reading. Cause: " + e.getMessage());
    }
  }

  /**
   * 初始化配置类
   * @param element 根节点
   */
  private synchronized void initialize(Element element) {
    MinipikaXMLConfig minipikaXMLConfig = Factorys.forClass(MinipikaXMLConfig.class);
    minipikaXMLConfig.parse(element);
  }

  private String perfectPath(String path) {
    return Threads.getCallerLoader().getResource(path).toString();
  }

}
