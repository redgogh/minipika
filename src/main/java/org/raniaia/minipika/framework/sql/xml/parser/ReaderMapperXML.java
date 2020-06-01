package org.raniaia.minipika.framework.sql.xml.parser;

/*
 * Copyright (C) 2020 Tiansheng All rights reserved.
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
 * Creates on 2019/12/16.
 */

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.raniaia.minipika.components.config.GlobalConfig;
import org.raniaia.minipika.components.logging.Log;
import org.raniaia.minipika.components.logging.LogFactory;
import org.raniaia.minipika.framework.sql.xml.node.XMLNode;
import org.raniaia.minipika.framework.exception.BuilderXmlException;
import org.raniaia.minipika.framework.sql.xml.node.XMLMapperNode;
import org.raniaia.minipika.framework.tools.Arrays;
import org.raniaia.minipika.framework.tools.MinipikaUtils;
import org.raniaia.minipika.framework.tools.StringUtils;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tiansheng
 */
public class ReaderMapperXML {

  static final Log log = LogFactory.getLog(ReaderMapperXML.class);

  /**
   * 解析xml中的标签
   */
  private MapperLabelParser xmlparser = new MapperLabelParser();
  private ReaderCrudElement readerCrud = new ReaderCrudElement();

  private List<XMLMapperNode> mappers = new ArrayList<>();

  /**
   * 获取xml文件列表
   *
   * @return
   */
  private List<File> getBuilderXMLFiles() {
    try {
      return MinipikaUtils.getMapperXMLs();
    } catch (Exception e) {
      if (e instanceof NullPointerException) {
        String array = Arrays.toString(GlobalConfig.getConfig().getMapperBasePackage(), ",");
        log.error("Error reading xml file, Cause: " + array + " directory nothing, please check " +
                "if the entered address is correct.");
        throw new NullPointerException("Error reading xml file, Cause: " + array + " directory nothing, please check " +
                "if the entered address is correct.");
      }
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 读取并解析XML
   * XML to {@link XMLNode}
   *
   * @throws Exception
   */
  public List<XMLMapperNode> parseXML() {
    try {
      SAXBuilder saxBuilder = new SAXBuilder();

      /*
       * 开始解析mapper xml
       */
      for (File mapperXML : getBuilderXMLFiles()) {

        Document document = saxBuilder.build(mapperXML);
        Element rootElement = document.getRootElement();

        String builderName = rootElement.getAttributeValue("name");

        if (StringUtils.isEmpty(builderName))
          throw new BuilderXmlException("builder label attribute \"name\" cannot null");

        xmlparser.setCurrentBuilder(builderName); // 设置当前Mapper name

        List<Element> crudLabels = rootElement.getChildren();
        mappers.add(readerCrud.reader(crudLabels, xmlparser));

      }

      return mappers;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

}
