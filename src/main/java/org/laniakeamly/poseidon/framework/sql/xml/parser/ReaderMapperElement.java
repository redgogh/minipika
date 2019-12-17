package org.laniakeamly.poseidon.framework.sql.xml.parser;

import org.jdom2.Content;
import org.jdom2.Element;
import org.laniakeamly.poseidon.framework.exception.runtime.ExpressionException;
import org.laniakeamly.poseidon.framework.sql.ProvideConstant;
import org.laniakeamly.poseidon.framework.sql.xml.node.XMLNode;
import org.laniakeamly.poseidon.framework.sql.xml.node.XMLBuilderNode;
import org.laniakeamly.poseidon.framework.sql.xml.node.XMLMapperNode;
import org.laniakeamly.poseidon.framework.tools.StringUtils;

import java.util.List;

/**
 * 读取Mapper并解析
 * Create by 2BKeyboard on 2019/12/17 10:58
 */
@SuppressWarnings("SpellCheckingInspection")
public class ReaderMapperElement {

    /**
     * 解析Mapper中标签的方法
     */
    private MapperLabelParser xmlparser;

    public XMLBuilderNode reader(List<Element> mappers, MapperLabelParser xmlparser) {

        this.xmlparser = xmlparser;
        XMLBuilderNode xmlBuilder = new XMLBuilderNode(xmlparser.getCurrentBuilder());

        for (Element mapper : mappers) {

            XMLMapperNode xmlMapper = new XMLMapperNode();

            String mappername = mapper.getAttributeValue("name");
            xmlMapper.setName(mappername);
            if (StringUtils.isEmpty(mappername)) {
                throw new ExpressionException("tag: mapper attribute name cannot null from builder '" + xmlparser.getCurrentBuilder() + "'");
            }
            xmlparser.setCurrentMapper(mappername);
            //
            // 解析mapper
            //
            parserMapperContent(mapper.getContent(), xmlMapper);
            xmlBuilder.addMapper(xmlMapper);

        }

        return xmlBuilder;
    }

    /**
     * 解析mapper标签下的内容
     * @param contents
     */
    public void parserMapperContent(List<Content> contents, XMLMapperNode xmlMapper) {

        for (Content content : contents) {

            if (content.getCType() == Content.CType.Text) {
                String text = StringUtils.trim(content.getValue());
                if (!StringUtils.isEmpty(text)) {
                    xmlMapper.addNode(new XMLNode(ProvideConstant.TEXT, text));
                }
                continue;
            }

            if (content.getCType() == Content.CType.Element) {
                Element element = ((Element) content);
                //
                // 如果是if标签
                //
                if (ProvideConstant.IF.equals(element.getName())) {
                    xmlMapper.addNode(xmlparser.ifOrEels(element));
                }
                //
                // 如果是choose标签
                //
                if (ProvideConstant.CHOOSE.equals(element.getName())) {
                    xmlMapper.addNode(xmlparser.choose(element));
                }
                continue;
            }

        }

    }

}
