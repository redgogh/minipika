package org.raniaia.poseidon.framework.sql.xml.parser;

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
 * Copyright: Create by tiansheng on 2019/12/16 23:23
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
