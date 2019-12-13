package com.poseidon.framework.sql.builder;


import com.poseidon.framework.exception.BuilderXmlException;
import com.poseidon.framework.tools.PoseidonUtils;
import com.poseidon.framework.tools.StringUtils;
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
        String sbo = JavaElement.STRING_BUILDER_OBJECT;
        for (File mapperXML : getBuilderXMLFiles()) {

            StringBuilder builder = new StringBuilder();

            Document document = saxBuilder.build(mapperXML);
            Element rootElement = document.getRootElement();

            List<Element> mappers = rootElement.getChildren();
            for (Element mapper : mappers) {

                //
                // mapper标签的name属性内容
                //
                String nameValue = mapper.getAttribute("name").getValue();
                MethodBuilder methodBuilder = new MethodBuilder();
                methodBuilder.createMethod("public static {} {}", "sql",
                        JavaElement.STRING_OBJECT, nameValue);
                methodBuilder.insert(sbo.concat(" sql = ").concat(sbo).concat("();"));
                for (Content content : mapper.getContent()) {
                    //
                    // 如果是文本文件
                    //
                    if (content.getCType() == Content.CType.Text) {
                        methodBuilder.insert("sql.append(\"".concat(StringUtils.trim(content.getValue())).concat("\");"));
                    }

                    //
                    // 如果是标签
                    //
                    if (content.getCType() == Content.CType.Element) {
                        Element element = ((Element) content);
                        String elementName = element.getName();
                        if ("choose".equals(elementName)) {
                            List<Element> chooseChildren = element.getChildren();
                            chooseCheck(chooseChildren, nameValue);
                        }

                        if ("if".equals(elementName)) {

                        }
                    }

                }
                System.out.println(methodBuilder.toString());


            }
        }
    }

    /**
     * 检测choose标签是否满足规范
     * @param chooseChildren choose标签下的element
     * @param nameValue      当前choose在哪个mapper下
     */
    private void chooseCheck(List<Element> chooseChildren, String nameValue) {
        if(chooseChildren.size() <= 0)
            throw new BuilderXmlException("tag: choose cannot if and else tag in " + nameValue);
        Element _if = chooseChildren.get(0);
        if(chooseChildren.size() > 0 && !"if".equals(_if.getName()))
            throw new BuilderXmlException("tag: choose at least include a if label" + nameValue);
        if(StringUtils.isEmpty(_if.getAttribute("test").getValue()))
            throw new BuilderXmlException("tag: choose in if attribute test cannot null in " + nameValue);
    }

    public static void main(String[] args) throws Exception {
        ReaderBuilder readerBuilder = new ReaderBuilder();
        readerBuilder.parseXML();
    }

}
