package org.laniakeamly.poseidon.framework.sql.parser;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.laniakeamly.poseidon.framework.sql.build.Node;
import org.laniakeamly.poseidon.framework.exception.BuilderXmlException;
import org.laniakeamly.poseidon.framework.sql.build.XMLBuilder;
import org.laniakeamly.poseidon.framework.tools.PoseidonUtils;
import org.laniakeamly.poseidon.framework.tools.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Create by 2BKeyboard on 2019/12/16 23:23
 */
public class ReaderBuilder {

    /**
     * 解析xml中的标签
     */
    @SuppressWarnings("SpellCheckingInspection")
    private MapperParser xmlparser = new MapperParser();
    private ReaderMapper readerMapper = new ReaderMapper();

    private List<XMLBuilder> builders = new ArrayList();

    /**
     * 获取xml文件列表
     * @return
     */
    private List<File> getBuilderXMLFiles() {
        return PoseidonUtils.getMapperXMLs();
    }

    /**
     * 读取并解析XML
     * XML to {@link Node}
     * @throws Exception
     */
    public List<XMLBuilder> parseXML() throws Exception {

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

            xmlparser.setCurrentBuilder(builderName); // 设置当前BuilderName

            List<Element> mappers = rootElement.getChildren();
            builders.add(readerMapper.reader(mappers, xmlparser));

        }

        return builders;

    }

    public static void main(String[] args) throws Exception {
        new ReaderBuilder().parseXML();
    }

}
