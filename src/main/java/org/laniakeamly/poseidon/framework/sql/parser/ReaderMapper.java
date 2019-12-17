package org.laniakeamly.poseidon.framework.sql.parser;

import org.jdom2.Content;
import org.jdom2.Element;
import org.laniakeamly.poseidon.framework.exception.ExpressionException;
import org.laniakeamly.poseidon.framework.sql.build.Node;
import org.laniakeamly.poseidon.framework.sql.build.XMLMapper;
import org.laniakeamly.poseidon.framework.tools.StringUtils;

import java.util.List;

/**
 * Create by 2BKeyboard on 2019/12/17 10:58
 */
public class ReaderMapper {

    private MapperParser mapperParser = new MapperParser();

    @SuppressWarnings("SpellCheckingInspection")
    public void reader(List<Element> mappers, MapperParser xmlparser) {

        for (Element mapper : mappers) {

            XMLMapper xmlMapper = new XMLMapper();

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
            System.out.println();
        }

    }

    /**
     * 解析mapper标签下的内容
     * @param contents
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void parserMapperContent(List<Content> contents, XMLMapper xmlMapper) {

        for (Content content : contents) {

            if (content.getCType() == Content.CType.Text) {
                String text = StringUtils.trim(content.getValue());
                if(!StringUtils.isEmpty(text)) {
                    xmlMapper.addNode(new Node("text", text));
                }
                continue;
            }

            if (content.getCType() == Content.CType.Element) {
                Element element = ((Element) content);
                //
                // 如果是if标签
                //
                if("if".equals(element.getName())){
                    xmlMapper.addNode(mapperParser.ifOrEels(element));
                }
                //
                // 如果是choose标签
                //
                if("choose".equals(element.getName())){
                    xmlMapper.addNode(mapperParser.choose(element));
                }
                continue;
            }

        }

    }

}
