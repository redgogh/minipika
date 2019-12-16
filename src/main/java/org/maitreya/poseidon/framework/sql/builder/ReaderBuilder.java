package org.maitreya.poseidon.framework.sql.builder;

import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.maitreya.poseidon.framework.exception.BuilderXmlException;
import org.maitreya.poseidon.framework.sql.parser.XMLParser;
import org.maitreya.poseidon.framework.tools.PoseidonUtils;
import org.maitreya.poseidon.framework.tools.StringUtils;

import java.io.File;
import java.util.List;

/**
 * Create by 2BKeyboard on 2019/12/16 23:23
 */
public class ReaderBuilder {

    /**
     * 解析xml中的标签
     */
    private XMLParser parser = new XMLParser();

    /**
     * 获取xml文件列表
     * @return
     */
    private List<File> getBuilderXMLFiles() {
        return PoseidonUtils.getMapperXMLs();
    }

    public void parseXML() throws Exception {

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

            parser.setCurrentBuilder(builderName); // 设置当前BuilderName

            List<Element> mappers = rootElement.getChildren();

            for (Element mapper : mappers) {

                //
                // mapper标签的name属性内容
                //
                String mapperName = mapper.getAttribute("name").getValue();
                parser.setCurrentMapper(mapperName); // 设置当前MapperName
                for (Content content : mapper.getContent()) {

                    //
                    // 如果是文本内容
                    //
                    if (content.getCType() == Content.CType.Text) {

                    }

                    //
                    // 如果是节点
                    //
                    if (content.getCType() == Content.CType.Element) {
                        Element element = ((Element) content);
                        String elementName = element.getName();

                        //
                        // choose节点
                        //
                        if ("choose".equals(elementName)) {

                        }

                        //
                        // if节点
                        //
                        if ("if".equals(elementName)) {

                        }

                    }


                }
            }
        }
    }

}
