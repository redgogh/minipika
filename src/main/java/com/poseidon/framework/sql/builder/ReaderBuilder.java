package com.poseidon.framework.sql.builder;


import com.poseidon.framework.tools.PoseidonUtils;
import com.poseidon.framework.tools.StringUtils;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.io.*;
import java.util.List;

/**
 * 读取xml映射文件
 * Create by 2BKeyboard on 2019/12/13 0:52
 */
public class ReaderBuilder {

    /**
     * 获取pte文件列表
     * @return
     */
    private List<File> getBuilderXMLFiles() {
        return PoseidonUtils.getMapperXMLs();
    }

    public void parseXML() throws Exception {
        SAXBuilder saxBuilder = new SAXBuilder();
        for(File mapperXML : getBuilderXMLFiles()){
            Document document = saxBuilder.build(mapperXML);
            Element rootElement = document.getRootElement();
            List<Element> mappers = rootElement.getChildren();
            for (Element mapper : mappers) {

                //
                // mapper标签的name属性内容
                //
                String nameValue = mapper.getAttribute("name").getValue();
                for(Content content : mapper.getContent()){
                    //
                    // 如果是文本文件
                    //
                    if(content.getCType() == Content.CType.Text);

                    //
                    // 如果是标签
                    //
                    if(content.getCType() == Content.CType.Element);

                }


            }
        }
    }

    public static void main(String[] args) throws Exception {
        ReaderBuilder readerBuilder = new ReaderBuilder();
        readerBuilder.parseXML();
    }

}
