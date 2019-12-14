package com.poseidon.framework.sql.builder;


import com.poseidon.framework.exception.BuilderXmlException;
import com.poseidon.framework.tools.NewlineBuilder;
import com.poseidon.framework.tools.PoseidonUtils;
import com.poseidon.framework.tools.StringUtils;
import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 读取xml映射文件
 *
 * 将XML中的标签解析成JavaCode
 *      1. 解析builder
 *      2. 解析mapper
 *              |
 *              |--> Java Method name: findUserByName
 *                          |
 *                          |--> label to java code
 *
 *      3. 将解析完成的代码组装成Java字节码
 *      4. 将Java字节码使用ClassLoader加载到JVM
 *      5. 从内存中获取生成的字节码文件
 *      6. 将字节码执行方法装载到${@link com.poseidon.framework.beans.BeansManager}中提供调用
 *      7. 其他处理
 *
 * Create by 2BKeyboard on 2019/12/13 0:52
 */
public class ReaderBuilder {

    private Pattern params = Pattern.compile("\\{\\{(.*?)}}");

    /**
     * 获取xml文件列表
     * @return
     */
    private List<File> getBuilderXMLFiles() {
        return PoseidonUtils.getMapperXMLs();
    }

    public void parseXML() throws Exception {
        SAXBuilder saxBuilder = new SAXBuilder();
        String sbo = JavaElement.STRING_BUILDER_OBJECT;
        for (File mapperXML : getBuilderXMLFiles()) {

            Document document = saxBuilder.build(mapperXML);
            Element rootElement = document.getRootElement();

            List<Element> mappers = rootElement.getChildren();
            for (Element mapper : mappers) {

                //
                // mapper标签的name属性内容
                //
                String nameValue = mapper.getAttribute("name").getValue();
                MethodBuilder methodBuilder = new MethodBuilder();
                methodBuilder.createMethod("public static {} {}", "sql.toString()", JavaElement.STRING_OBJECT, nameValue);
                methodBuilder.insert(sbo.concat(" sql = new ").concat(sbo).concat("();"));


                for (Content content : mapper.getContent()) {

                    // 如果是文本文件
                    if (content.getCType() == Content.CType.Text) {
                        String value = content.getValue().trim();
                        if(!StringUtils.isEmpty(value)) {
                            methodBuilder.insert("sql.append(\"".concat(StringUtils.trim(value)).concat("\");"));
                        }
                        continue;
                    }

                    // 如果是节点
                    if (content.getCType() == Content.CType.Element) {
                        Element element = ((Element) content);
                        String elementName = element.getName();

                        //
                        // choose节点
                        //
                        if ("choose".equals(elementName)) {
                            List<Element> chooseChildren = element.getChildren();
                            chooseCheck(chooseChildren, nameValue);

                            String test = null;
                            NewlineBuilder _ifContent = null;
                            NewlineBuilder _elseContent = null;

                            for (Element chooseChild : chooseChildren) {

                                //
                                // if process
                                //
                                if ("if".equals(chooseChild.getName())) {
                                    // 获取if标签中的内容
                                    test = chooseChild.getAttributeValue("test");
                                    _ifContent = new NewlineBuilder(trim(chooseChild.getText()));
                                    // 判断是否存在<line>节点
                                    parseLineLabel(chooseChild, _ifContent);
                                    continue;
                                }

                                //
                                // else process
                                //
                                if ("else".equals(chooseChild.getName())) {
                                    _elseContent = new NewlineBuilder(trim(chooseChild.getText()));
                                    parseLineLabel(chooseChild, _elseContent);
                                }

                            }
                            NewlineBuilder jcode = toJavaCodeByChooseLabel(test, _ifContent, _elseContent);
                            addMethodContent(jcode, methodBuilder);
                        }

                        //
                        // if节点
                        //
                        if ("if".equals(elementName)) {
                            // 获取if标签中的内容
                            String test = element.getAttributeValue("test");
                            NewlineBuilder _ifContent = new NewlineBuilder(trim(element.getText()));
                            // 判断是否存在<line>节点
                            parseLineLabel(element, _ifContent);
                            NewlineBuilder jcode = toJavaCodeByChooseLabel(test, _ifContent, null);
                            addMethodContent(jcode, methodBuilder);
                        }
                    }
                }
                System.out.println(methodBuilder.toString());
            }
        }
    }

    public void addMethodContent(NewlineBuilder jcode, MethodBuilder methodBuilder) {
        if (jcode != null) {
            methodBuilder.insert(jcode);
        }
    }

    /**
     * 解析line标签
     * @param root 包含line标签的节点
     * @param builder 解析出来的数据存放到这个对象
     */
    private void parseLineLabel(Element root, NewlineBuilder builder) {
        // 判断是否存在<line>节点
        List<Element> children = root.getChildren();
        if (children.size() > 0) {
            for (Element child : children) {
                builder.appendLine(StringUtils.trim(child.getText()));
            }
        }
    }

    /**
     * 将choose标签中的内容转换成Java的逻辑代码
     *
     * @param test              表达式
     * @param _ifContent        if代码块中需要的内容
     * @param _elseContent      else代码块中需要的内容
     */
    private NewlineBuilder toJavaCodeByChooseLabel(String test, NewlineBuilder _ifContent,
                                                   NewlineBuilder _elseContent) {
        Map<String, String> _elseParams = null;
        Map<String, String> _ifParams = getParamNameAndLineData(_ifContent);
        if (_elseContent != null) {
            _elseParams = getParamNameAndLineData(_elseContent);
        }
        if (_ifParams == null) return null;
        NewlineBuilder codeBuilder = new NewlineBuilder();
        test = test.replaceAll("and", "&&");
        test = test.replaceAll("or", "||");
        test = test.replaceAll("'", "\"");

        StringBuilder expBuilder = new StringBuilder();

        for (Map.Entry<String, String> param : _ifParams.entrySet()) {
            String key = param.getKey();
            String values = param.getValue();
            expBuilder.append("if(");
            if (test.contains("$req")) {
                expBuilder.append(test.replaceAll("\\$req", key));
            } else {
                expBuilder.append(test);
            }
            expBuilder.append(")\n{\n");
            for (String value : values.split("@")) {
                expBuilder.append("\t#varable#.append(\"")
                        .append(value)
                        .append("\");\n");
            }
            expBuilder.append("}\n");

            if (_elseParams != null) {
                String _else = _elseParams.get(key);
                if (!StringUtils.isEmpty(_else)) {

                    expBuilder.append("else\n{\n")
                            .append("\t#varable#.append(\"")
                            .append(_else)
                            .append("\");\n")
                            .append("}\n")
                    ;
                }
            }
        }
        codeBuilder.appendLine(expBuilder.toString());
        expBuilder.delete(0, expBuilder.length());
        return codeBuilder;
    }

    /**
     * 获取参数变量以及所在行
     * @param builder   NewlineBuilder
     * @return Map
     */
    public Map<String, String> getParamNameAndLineData(NewlineBuilder builder) {
        Map<String, String> paramAndLine = null;
        while (builder.hasNext()) {
            String next = builder.next();
            Matcher matcher = params.matcher(next);
            while (matcher.find()) {
                String value = matcher.group(1);
                if (!StringUtils.isEmpty(value)) {
                    if (paramAndLine == null) paramAndLine = new HashMap<>();
                    if (paramAndLine.get(value) == null) {
                        paramAndLine.put(value, next);
                    } else {
                        paramAndLine.put(value, paramAndLine.get(value).concat("@").concat(next));
                    }
                }
            }
        }
        return paramAndLine;
    }

    /**
     * 清除xml中不合法的换行
     * @param str
     * @return
     */
    private String trim(String str) {
        if (StringUtils.isEmpty(str)) return "";
        NewlineBuilder result = new NewlineBuilder();
        NewlineBuilder builder = new NewlineBuilder(str);
        if (!builder.hasNext()) {
            result.appendLine(str);
        } else {
            while (builder.hasNext()) {
                String value = builder.next().trim();
                if (!StringUtils.isEmpty(value)) {
                    result.appendLine(value);
                }
            }
        }
        return result.toString();
    }

    /**
     * 检测choose标签是否满足规范
     * @param chooseChildren choose标签下的element
     * @param nameValue      当前choose在哪个mapper下
     */
    private void chooseCheck(List<Element> chooseChildren, String nameValue) {
        if (chooseChildren.size() <= 0)
            throw new BuilderXmlException("tag: choose cannot if and else tag in " + nameValue);
        Element _if = chooseChildren.get(0);
        if (chooseChildren.size() > 0 && !"if".equals(_if.getName()))
            throw new BuilderXmlException("tag: choose at least include a if label " + nameValue);
        if (StringUtils.isEmpty(_if.getAttribute("test").getValue()))
            throw new BuilderXmlException("tag: choose in if attribute test cannot null in " + nameValue);
    }

    public static void main(String[] args) throws Exception {
        ReaderBuilder readerBuilder = new ReaderBuilder();
        readerBuilder.parseXML();
    }

}
