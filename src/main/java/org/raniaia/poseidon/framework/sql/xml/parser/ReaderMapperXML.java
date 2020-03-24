package org.raniaia.poseidon.framework.sql.xml.parser;

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
import org.raniaia.poseidon.framework.sql.xml.node.XMLNode;
import org.raniaia.poseidon.framework.exception.runtime.BuilderXmlException;
import org.raniaia.poseidon.framework.sql.xml.node.XMLMapperNode;
import org.raniaia.poseidon.framework.tools.POFUtils;
import org.raniaia.poseidon.framework.tools.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tiansheng
 */
public class ReaderMapperXML {

    /**
     * 解析xml中的标签
     */
    @SuppressWarnings("SpellCheckingInspection")
    private MapperLabelParser xmlparser = new MapperLabelParser();
    private ReaderCrudElement readerCrud = new ReaderCrudElement();

    private List<XMLMapperNode> mappers = new ArrayList();

    /**
     * 获取xml文件列表
     * @return
     */
    private List<File> getBuilderXMLFiles() {
        return POFUtils.getMapperXMLs();
    }

    /**
     * 读取并解析XML
     * XML to {@link XMLNode}
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
