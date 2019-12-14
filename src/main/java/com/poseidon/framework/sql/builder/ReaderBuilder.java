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
                methodBuilder.createMethod("public static {} {}", "sql", JavaElement.STRING_OBJECT, nameValue);
                methodBuilder.insert(sbo.concat(" sql = ").concat(sbo).concat("();"));


                for (Content content : mapper.getContent()) {

                    // 如果是文本文件
                    if (content.getCType() == Content.CType.Text) {
                        methodBuilder.insert("sql.append(\"".concat(StringUtils.trim(content.getValue())).concat("\");"));
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
                                }

                                //
                                // else process
                                //
                                if ("else".equals(chooseChild.getName())) {
                                    _elseContent = new NewlineBuilder();
                                    parseLineLabel(chooseChild, _elseContent);
                                }

                            }
                            toJavaCodeByChooseLabel(test, _ifContent, _elseContent);
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
    private void toJavaCodeByChooseLabel(String test, NewlineBuilder _ifContent,
                                         NewlineBuilder _elseContent) {
        NewlineBuilder codeBuilder = new NewlineBuilder();
        test = test.replaceAll("and", "&&");
        test = test.replaceAll("or", "||");
        test = test.replaceAll("'", "\"");
        Map<String, String> _ifParams = getParamNameAndLineData(_ifContent);
        Map<String, String> _elseParams = getParamNameAndLineData(_elseContent);

        StringBuilder expBuilder = new StringBuilder();

        if (test.contains("$req")) {
            for (Map.Entry<String, String> param : _ifParams.entrySet()) {
                String key = param.getKey();
                String values = param.getValue();
                expBuilder.append("if(")
                        .append(test.replaceAll("\\$req", key))
                        .append("){\n");
                for (String value : values.split("@")) {
                    expBuilder.append("\t#varable#.append(\"")
                            .append(value)
                            .append("\");\n");
                }
                expBuilder.append("}\n");
            }
            codeBuilder.appendLine(expBuilder.toString());
            expBuilder.delete(0, expBuilder.length());
        }
        System.out.println(codeBuilder.toString());
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
     * 解析if test属性中的内容
     * 可能出现的运算符有以下几种
     *
     *      1. ==
     *      2. !=
     *      3. <=
     *      4. >=
     *      5. <
     *      6. >
     *
     * @param test test属性
     * @return 解析后的JavaCode
     */
    private String iftest(String test) {
        StringBuilder result = new StringBuilder();
        StringBuilder content = new StringBuilder();

        for (char value : test.toCharArray()) {
            if (value == ' ') {
                String contentValue = content.toString();
                if (contentValue.equals("==")) {
                } else if (contentValue.equals("!=")) {
                } else if (contentValue.equals("<=")) {
                } else if (contentValue.equals(">=")) {
                } else if (contentValue.equals("<")) {
                } else if (contentValue.equals(">")) {
                } else if (contentValue.equals("or")) {
                } else if (contentValue.equals("and")) {
                } else {
                }
            }
            content.append(value);
        }

        return result.toString();
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
