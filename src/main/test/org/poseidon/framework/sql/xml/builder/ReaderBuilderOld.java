package org.poseidon.framework.sql.xml.builder;


import org.laniakeamly.poseidon.framework.exception.runtime.BuilderXmlException;
import org.laniakeamly.poseidon.framework.exception.runtime.ExpressionException;
import org.laniakeamly.poseidon.framework.sql.xml.ProvideConstant;
import org.laniakeamly.poseidon.framework.sql.xml.token.Token;
import org.laniakeamly.poseidon.framework.sql.xml.token.TokenValue;
import org.laniakeamly.poseidon.framework.tools.NewlineBuilder;
import org.laniakeamly.poseidon.framework.tools.POFUtils;
import org.laniakeamly.poseidon.framework.tools.StringUtils;
import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.laniakeamly.poseidon.framework.beans.BeansManager;

import java.io.File;
import java.util.*;
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
 *      6. 将字节码执行方法装载到${@link BeansManager}中提供调用
 *      7. 其他处理
 *
 * Copyright: Create by TianSheng on 2019/12/13 0:52
 */
@Deprecated
public class ReaderBuilderOld {

    private Pattern paramsPattern = Pattern.compile("\\{\\{(.*?)}}");

    private String fullPath = "com.poseidon.mapper.$class.";

    /**
     * $empty代表判断String是否为空
     * 调用{@link StringUtils} 中的isEmpty()方法
     * 假设当前条件为 name == $empty
     * 那么转换成Java代码后就是 StringUtils.isEmpty(name);
     */
    private String PROVIDE_EMPTY = "$empty";

    /**
     * 获取xml文件列表
     * @return
     */
    private List<File> getBuilderXMLFiles() {
        return POFUtils.getMapperXMLs();
    }

    public void parseXML() throws Exception {

        List<ClassBuilder> classBuilderList = new ArrayList<>();

        SAXBuilder saxBuilder = new SAXBuilder();
        String sbo = JavaElement.STRING_BUILDER_OBJECT;

        /*
         * 开始解析mapper xml
         */
        for (File mapperXML : getBuilderXMLFiles()) {

            Document document = saxBuilder.build(mapperXML);
            Element rootElement = document.getRootElement();

            String builderName = rootElement.getAttributeValue("name");
            if (StringUtils.isEmpty(builderName))
                throw new BuilderXmlException("builder label attribute \"name\" cannot null");

            List<Element> mappers = rootElement.getChildren();
            // 构建一个ClassBuilder
            ClassBuilder classBuilder = new ClassBuilder(builderName, fullPath);
            classBuilder.createClassStatement();

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
                        if (!StringUtils.isEmpty(value)) {
                            String sqlValue = StringUtils.trim(value);
                            String addSQL = ProvideConstant.sqlAppendProcess(sqlValue);
                            methodBuilder.insert(addSQL);
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
                        if (ProvideConstant.CHOOSE.equals(elementName)) {
                            List<Element> chooseChildren = element.getChildren();
                            chooseCheck(chooseChildren, nameValue);

                            String test = null;
                            NewlineBuilder _ifContent = null;
                            NewlineBuilder _elseContent = null;

                            for (Element chooseChild : chooseChildren) {

                                //
                                // if process
                                //
                                if (ProvideConstant.IF.equals(chooseChild.getName())) {
                                    // 获取if标签中的内容
                                    test = chooseChild.getAttributeValue(ProvideConstant.IF_TEST);
                                    _ifContent = new NewlineBuilder(trim(chooseChild.getText()));
                                    // 判断是否存在<line>节点
                                    parseLineLabel(chooseChild, _ifContent);
                                    continue;
                                }

                                //
                                // else process
                                //
                                if (ProvideConstant.ELSE.equals(chooseChild.getName())) {
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
                        if (ProvideConstant.IF.equals(elementName)) {
                            // 获取if标签中的内容
                            String test = element.getAttributeValue(ProvideConstant.IF_TEST);
                            NewlineBuilder _ifContent = new NewlineBuilder(trim(element.getText()));
                            // 判断是否存在<line>节点
                            parseLineLabel(element, _ifContent);
                            NewlineBuilder jcode = toJavaCodeByChooseLabel(test, _ifContent, null);
                            addMethodContent(jcode, methodBuilder);
                        }
                    }
                }

                //
                // 解析完后开始添加参数
                //
                Matcher matcher = paramsPattern.matcher(methodBuilder.toString());
                String args = "";
                while (matcher.find()) {
                    String value = matcher.group(1);
                    args = args.concat(JavaElement.OBJECT).concat(" ").concat(value).concat(", ");
                }
                if (!StringUtils.isEmpty(args)) {
                    args = args.substring(0, args.lastIndexOf(","));
                }
                methodBuilder.setArgs(args);
                classBuilder.putMethod(methodBuilder);
            }
            classBuilderList.add(classBuilder);
        }
    }

    /**
     * 添加解析后的代码
     * @param jcode             javaCode我简写成jcode
     * @param methodBuilder     methodBuilder对象
     */
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

        StringBuilder expBuilder = new StringBuilder();

        for (Map.Entry<String, String> param : _ifParams.entrySet()) {
            String key = param.getKey();
            String values = param.getValue();
            expBuilder.append("if(");
            expBuilder.append(testProcess(test));
            expBuilder.append(") { ");
            for (String value : values.split("@")) {
                expBuilder.append(" sql.append(\" ")
                        .append(value)
                        .append(" \"); ");
            }
            expBuilder.append("} ");

            if (_elseParams != null) {
                String _else = _elseParams.get(key);
                if (!StringUtils.isEmpty(_else)) {

                    expBuilder.append("else { ")
                            .append("\tsql.append(\" ")
                            .append(_else)
                            .append(" \"); ")
                            .append("} ")
                    ;
                }
            }
        }
        codeBuilder.appendLine(expBuilder.toString());
        expBuilder.delete(0, expBuilder.length());
        return codeBuilder;
    }

    /**
     * 处理test中的表达式
     * @param test test表达式内容
     * @return
     */
    @SuppressWarnings({"all"})
    private String testProcess(String test) {
        boolean isString = false;
        // test = test.replaceAll("'", "\"");
        test = test.replaceAll(" or ", " || ");
        test = test.replaceAll(" and ", " && ");
        char[] charArray = test.toCharArray();
        StringBuilder builder = new StringBuilder();
        List<TokenValue> tokens = new LinkedList<>();
        // Map<String, TestToken> testMap = new LinkedHashMap<>();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            // 处理String
            if (isString) {
                if (c == '\'') {
                    isString = false;
                    builder.append("\"");
                    tokens.add(TokenValue.buildToken(Token.IDEN, builder.toString()));
                    clear(builder);
                } else {
                    builder.append(c);
                }
                continue;
            }

            if (c == ' ') continue; // 如果不是String内扫描到空格直接跳过

            switch (c) {
                case '=': {
                    String builderValue = builder.toString();
                    if (!builderValue.equals("=")
                            && !builderValue.equals("!")
                            && !builderValue.equals("<")
                            && !builderValue.equals(">")) {
                        if (!StringUtils.isEmpty(builder.toString())) {
                            tokens.add(TokenValue.buildToken(Token.IDEN, builder.toString()));
                        }
                        clear(builder);
                        builder.append(c);
                        break;
                    } else {
                        builder.append(c);
                        if (!StringUtils.isEmpty(builder.toString())) {
                            String value = builder.toString();
                            if (value.equals("=="))
                                tokens.add(TokenValue.buildToken(Token.EQ, Token.OP, value));
                            if (value.equals("!="))
                                tokens.add(TokenValue.buildToken(Token.NE, Token.OP, value));
                            if (value.equals("<"))
                                tokens.add(TokenValue.buildToken(Token.LT, Token.OP, value));
                            if (value.equals("<="))
                                tokens.add(TokenValue.buildToken(Token.LE, Token.OP, value));
                            if (value.equals(">"))
                                tokens.add(TokenValue.buildToken(Token.GE, Token.OP, value));
                            if (value.equals(">="))
                                tokens.add(TokenValue.buildToken(Token.GT, Token.OP, value));
                        }
                        clear(builder);
                        break;
                    }
                }
                case '!': {
                    if (!StringUtils.isEmpty(builder.toString())) {
                        tokens.add(TokenValue.buildToken(Token.IDEN, builder.toString()));
                    }
                    clear(builder);
                    builder.append(c);
                    break;
                }
                case '<': {
                    if (!StringUtils.isEmpty(builder.toString())) {
                        tokens.add(TokenValue.buildToken(Token.IDEN, builder.toString()));
                    }
                    clear(builder);
                    builder.append(c);
                    break;
                }
                case '>': {
                    if (!StringUtils.isEmpty(builder.toString())) {
                        tokens.add(TokenValue.buildToken(Token.IDEN, builder.toString()));
                    }
                    clear(builder);
                    builder.append(c);
                    break;
                }
                case '&': {
                    String builderValue = builder.toString();
                    if (!builderValue.equals("&")) {
                        if (!StringUtils.isEmpty(builder.toString())) {
                            tokens.add(TokenValue.buildToken(Token.IDEN, builder.toString()));
                        }
                        clear(builder);
                        builder.append(c);
                    } else {
                        builder.append(c);
                        if (!StringUtils.isEmpty(builder.toString())) {
                            tokens.add(TokenValue.buildToken(Token.AND, builder.toString()));
                        }
                        clear(builder);
                    }
                    break;
                }
                case '|': {
                    String builderValue = builder.toString();
                    if (!builderValue.equals("|")) {
                        if (!StringUtils.isEmpty(builder.toString()))
                            tokens.add(TokenValue.buildToken(Token.IDEN, builderValue));
                        clear(builder);
                        builder.append(c);
                    } else {
                        builder.append(c);
                        if (!StringUtils.isEmpty(builder.toString())) {
                            tokens.add(TokenValue.buildToken(Token.OR, builder.toString()));
                        }
                        clear(builder);
                    }
                    break;
                }
                case '\'': {
                    if (!isString) {
                        builder.append("\"");
                        isString = true;
                    }
                    break;
                }
                default: {
                    builder.append(c);
                }
            }
        }
        tokens.add(TokenValue.buildToken(Token.IDEN, builder.toString()));
        clear(builder);

        // todo 将变量转成从Map中获取,将基本数据类型包括String的转换成equals
        StringBuilder result = new StringBuilder();

        for (int i = 0, len = tokens.size(); i < len; i++) {
            TokenValue tv = tokens.get(i);
            String value = tv.getValue();
            Token root = tv.getRoot();
            Token token = tv.getToken();

            if (root == Token.OP) {

                TokenValue next = tokens.get(i + 1);
                TokenValue previous = tokens.get(i - 1);
                String exps = toJavaCodeByTokenValue(previous, next, tv);
            }


        }

        return test;
    }

    /**
     * 将条件转换为Java代码
     * @param previous
     * @param next
     */
    private String toJavaCodeByTokenValue(TokenValue previous, TokenValue next, TokenValue op) {
        String nextValue = next.getValue();
        Token nextToken = getToken(nextValue);
        String previousValue = checkPrevious(previous);
        Token previousToken = getToken(previousValue);
        StringBuilder result = new StringBuilder();

        Token opToken = op.getToken();

        System.out.println();

        return result.toString();
    }

    private String format(String value, Object... args) {
        return StringUtils.format(value, args);
    }

    /**
     * 获取value类型
     * @param value
     * @return
     */
    private Token getToken(String value) {
        if ("\"".equals(StringUtils.getFirstCharacter(value))
                && "\"".equals(StringUtils.getLastCharacter(value))) {
            return Token.STRING;
        }
        if ("$".equals(StringUtils.getFirstCharacter(value))) {
            return Token.PROVIDE;
        }
        if ("null".equals(value)) {
            return Token.NULL;
        }
        if (StringUtils.isNumber(value)) {
            return Token.NUMBER;
        }
        return Token.VARIABLE;
    }

    /**
     * 清空StringBuilder
     * @param builder 被清空的对象
     */
    private void clear(StringBuilder builder) {
        builder.delete(0, builder.length());
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
            Matcher matcher = paramsPattern.matcher(next);
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
        if (chooseChildren.size() > 0 && !ProvideConstant.IF.equals(_if.getName()))
            throw new BuilderXmlException("tag: choose at least include a if label " + nameValue);
        if (StringUtils.isEmpty(_if.getAttribute(ProvideConstant.IF_TEST).getValue()))
            throw new BuilderXmlException("tag: choose in if attribute test cannot null in " + nameValue);
    }

    /**
     * 检查条件的第一个值是否符合要求
     * @param previous
     */
    private String checkPrevious(TokenValue previous) {
        if ("$".equals(StringUtils.getFirstCharacter(previous.getValue())))
            throw new ExpressionException("condition the first value cannot is poseidon provide the object：" + previous.getValue());
        return previous.getValue();
    }

    /**
     * 检查判断String类型是否符合条件
     * @param previousToken
     * @param nextToken
     */
    private void checkCondition(Token previousToken, Token nextToken) {
        if (nextToken == Token.STRING) {
            if (previousToken != Token.VARIABLE && previousToken != Token.PROVIDE && previousToken != Token.STRING)
                throw new ExpressionException("test content error: string cannot compare " + String.valueOf(previousToken).toLowerCase());
        }
        if (nextToken == Token.NUMBER) {
            if (previousToken != Token.VARIABLE && previousToken != Token.NUMBER)
                throw new ExpressionException("test content error: string cannot compare " + String.valueOf(previousToken).toLowerCase());
        }
    }

    public static void main(String[] args) throws Exception {
        ReaderBuilderOld readerBuilder = new ReaderBuilderOld();
        readerBuilder.parseXML();
        // System.out.println(readerBuilder.testProcess("1 != $empty"));
    }

}
