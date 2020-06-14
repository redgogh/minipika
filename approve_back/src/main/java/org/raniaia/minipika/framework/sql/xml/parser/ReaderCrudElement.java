package org.jiakesimk.minipika.framework.sql.xml.parser;

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
 * Creates on 2019/12/17.
 */

import org.jdom2.Content;
import org.jdom2.Element;
import org.jiakesimk.minipika.framework.exception.ExpressionException;
import org.jiakesimk.minipika.framework.exception.MapperXMLException;
import org.jiakesimk.minipika.framework.provide.ProvideVar;
import org.jiakesimk.minipika.framework.sql.xml.node.XMLNode;
import org.jiakesimk.minipika.framework.sql.xml.node.XMLMapperNode;
import org.jiakesimk.minipika.framework.sql.xml.node.XMLDynamicSqlNode;
import org.jiakesimk.minipika.framework.tools.StringUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * 读取Mapper并解析
 * @author tiansheng
 */
@SuppressWarnings("SpellCheckingInspection")
public class ReaderCrudElement {

    /**
     * 解析Mapper中标签的方法
     */
    private MapperLabelParser xmlparser;

    public XMLMapperNode reader(List<Element> cruds, MapperLabelParser xmlparser) {

        this.xmlparser = xmlparser;
        XMLMapperNode xmlMapperNode = new XMLMapperNode(xmlparser.getCurrentBuilder());

        for (Element crud : cruds) {

            XMLDynamicSqlNode dynamicSql = new XMLDynamicSqlNode();

            // 获取crud标签属性
            String type = crud.getName();
            String result = crud.getAttributeValue("result");
            String mappername = crud.getAttributeValue("name");
            dynamicSql.setType(type);
            dynamicSql.setResult(result);
            dynamicSql.setName(mappername);
            if (StringUtils.isEmpty(mappername)) {
                throw new ExpressionException("tag: mapper attribute name cannot null from builder '" + xmlparser.getCurrentBuilder() + "'");
            }
            xmlparser.setCurrentMapper(mappername);
            //
            // 解析mapper
            //
            parserMapperContent(crud.getContent(), dynamicSql);
            xmlMapperNode.add(dynamicSql);

        }

        return xmlMapperNode;
    }

    /**
     * 解析mapper标签下的内容
     * @param contents
     */
    public void parserMapperContent(List<Content> contents, XMLDynamicSqlNode dynamicSql) {
        List<XMLNode> nodes = new LinkedList<>();
        parseContents(contents, nodes);
        for (XMLNode node : nodes) {
            dynamicSql.addNode(node);
        }
    }

    /**
     * 解析标签
     * @param contents
     * @return
     */
    public void parseContents(List<Content> contents, List<XMLNode> nodes) {

        for (Content content : contents) {

            if (content.getCType() == Content.CType.Text) {
                String text = StringUtils.trim(content.getValue());
                if (!StringUtils.isEmpty(text)) {
                    nodes.add(new XMLNode(ProvideVar.TEXT, text));
                }
                continue;
            }

            if (content.getCType() == Content.CType.Element) {
                Element element = ((Element) content);
                //
                // if
                //
                if (ProvideVar.IF.equals(element.getName())) {
                    nodes.add(xmlparser.ifOrEels(element));
                    continue;
                }

                //
                // choose
                //
                if (ProvideVar.CHOOSE.equals(element.getName())) {
                    nodes.add(xmlparser.choose(element));
                    continue;
                }

                //
                // foreach
                //
                if (ProvideVar.FOREACH.equals(element.getName())) {
                    XMLNode forNode = xmlparser.foreach(element);
                    if (element.getContent().size() > 0) {
                        List<XMLNode> forNodes = new LinkedList<>();
                        parseContents(element.getContent(), forNodes);
                        for (XMLNode node : forNodes) {
                            forNode.addChild(node);
                        }
                    }
                    nodes.add(forNode);
                    continue;
                }

                //
                // parameter
                //
                if (ProvideVar.PARAMETER.equals(element.getName())) {
                    XMLNode parameter = new XMLNode(element.getName(),StringUtils.trim(element.getText()));
                    nodes.add(parameter);
                    continue;
                }

                throw new MapperXMLException("unknown lbael '" + element.getName() + "' error location in mapper: "
                        + xmlparser.getCurrentBuilder() + " mapper " + xmlparser.getCurrentMapper());
            }

        }
    }

}
