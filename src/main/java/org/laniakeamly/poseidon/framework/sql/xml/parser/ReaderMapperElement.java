package org.laniakeamly.poseidon.framework.sql.xml.parser;

import org.jdom2.Content;
import org.jdom2.Element;
import org.laniakeamly.poseidon.framework.exception.runtime.ExpressionException;
import org.laniakeamly.poseidon.framework.exception.runtime.MapperXMLException;
import org.laniakeamly.poseidon.framework.sql.ProvideConstant;
import org.laniakeamly.poseidon.framework.sql.xml.node.XMLNode;
import org.laniakeamly.poseidon.framework.sql.xml.node.XMLBuilderNode;
import org.laniakeamly.poseidon.framework.sql.xml.node.XMLMapperNode;
import org.laniakeamly.poseidon.framework.tools.StringUtils;
import org.omg.CORBA.portable.UnknownException;

import java.util.LinkedList;
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

            // 获取mapper标签属性
            String result = mapper.getAttributeValue("result");
            String mappername = mapper.getAttributeValue("name");
            xmlMapper.setName(mappername);
            xmlMapper.setResult(result);
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
        List<XMLNode> nodes = new LinkedList<>();
        parseContents(contents, nodes);
        for (XMLNode node : nodes) {
            xmlMapper.addNode(node);
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
                    nodes.add(new XMLNode(ProvideConstant.TEXT, text));
                }
                continue;
            }

            if (content.getCType() == Content.CType.Element) {
                Element element = ((Element) content);
                //
                // if标签
                //
                if (ProvideConstant.IF.equals(element.getName())) {
                    nodes.add(xmlparser.ifOrEels(element));
                    continue;
                }

                //
                // choose标签
                //
                if (ProvideConstant.CHOOSE.equals(element.getName())) {
                    nodes.add(xmlparser.choose(element));
                    continue;
                }

                //
                // foreach标签
                //
                if (ProvideConstant.FOREACH.equals(element.getName())) {
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

                throw new MapperXMLException("unknown lbael '" + element.getName() + "' error location in builder: "
                        + xmlparser.getCurrentBuilder() + " mapper " + xmlparser.getCurrentMapper());
            }

        }
    }

}
