package org.jiakesiws.minipika.framework.sql.xml.parser;

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
 * Creates on 2019/12/17.
 */

import lombok.Getter;
import lombok.Setter;
import org.jdom2.Content;
import org.jdom2.Element;
import org.jiakesiws.minipika.framework.exception.ExpressionException;
import org.jiakesiws.minipika.framework.provide.ProvideVar;
import org.jiakesiws.minipika.framework.sql.xml.node.XMLNode;
import org.jiakesiws.minipika.framework.tools.StringUtils;

import java.util.List;

/**
 * xml mapper下的标签解析
 * @author 2B键盘
 * @email jiakesiws@gmail.com
 */
@SuppressWarnings("SpellCheckingInspection")
public class MapperLabelParser implements MapperLabelParserService {

    // 解析工具类
    private XMLParserUtils util = new XMLParserUtils();

    // 语法错误检测
    private GrammarCheck grammarCheck = new GrammarCheck();

    @Setter
    @Getter
    private String currentBuilder;

    @Setter
    @Getter
    private String currentMapper;

    @Override
    public XMLNode text(Content content) {
        return null;
    }

    @Override
    public XMLNode ifOrEels(Element element) {
        String ieName = element.getName();
        XMLNode ieNode = new XMLNode(ieName);
        if (ProvideVar.IF.equals(ieName)) {
            String test = util.getIfLabelTestAttribute(element);
            if (StringUtils.isEmpty(test))
                throw new ExpressionException("tag: if label attribute test content cannot null. in mapper "
                +currentBuilder + " : "+currentMapper);
            ieNode.addAttribute(ProvideVar.IF_TEST, test);
        }
        List<Content> conditions = element.getContent();
        int condCount = 0;
        for (Content condition : conditions) {
            // 文本
            if (condition.getCType() == Content.CType.Text) {
                String text = util.trim(condition.getValue());
                if (!StringUtils.isEmpty(text)) {
                    ieNode.addChild(new XMLNode(ProvideVar.TEXT, text));
                }
                continue;
            }
            // 标签
            if (condition.getCType() == Content.CType.Element) {
                Element condElement = ((Element) condition);
                XMLNode cond = new XMLNode(condElement.getName(), util.trim(condElement.getValue()));
                cond.addAttribute(ProvideVar.COND_ATTRIBUTE_KEY,String.valueOf(condCount));
                condCount++;
                ieNode.addChild(cond);
            }
        }
        return ieNode;
    }

    @Override
    public XMLNode choose(Element element) {
        List<Element> chooseChildren = element.getChildren();
        grammarCheck.chooseCheck(chooseChildren, currentBuilder, currentMapper);
        String ename = element.getName();
        XMLNode chooseNode = new XMLNode(ename);
        for (Element child : chooseChildren) {
            chooseNode.addChild(ifOrEels(child));
        }
        return chooseNode;
    }

    @Override
    public XMLNode foreach(Element element) {
        XMLNode eachNode = new XMLNode(element.getName());
        eachNode.addAttribute(ProvideVar.ITEM,element.getAttributeValue(ProvideVar.ITEM));
        eachNode.addAttribute(ProvideVar.INDEX,element.getAttributeValue(ProvideVar.INDEX));
        eachNode.addAttribute(ProvideVar.COLLECTIONS,element.getAttributeValue(ProvideVar.COLLECTIONS));
        return eachNode;
    }
}
