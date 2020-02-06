package org.laniakeamly.poseidon.framework.sql.xml.build;

import org.laniakeamly.poseidon.framework.exception.runtime.DynamicSQLException;
import org.laniakeamly.poseidon.framework.exception.runtime.ExpressionException;
import org.laniakeamly.poseidon.framework.sql.xml.ProvideConstant;
import org.laniakeamly.poseidon.framework.sql.xml.node.XMLDynamicSqlNode;
import org.laniakeamly.poseidon.framework.sql.xml.node.XMLMapperNode;
import org.laniakeamly.poseidon.framework.sql.xml.node.XMLNode;
import org.laniakeamly.poseidon.framework.sql.xml.token.Token;
import org.laniakeamly.poseidon.framework.sql.xml.token.TokenValue;
import org.laniakeamly.poseidon.framework.tools.StringUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 将CrudNode转换成Java代码
 * Copyright: Create by 2BKeyboard on 2019/12/17 17:51
 */
public class ParserCrudNode {

    /**
     * mapper name用于异常追踪
     */
    private String mapperName;

    /**
     * builder name用于异常追踪
     */
    private String builderName;

    public PrecompiledMethod parse(XMLDynamicSqlNode mapperNode, XMLMapperNode builderNode) {
        this.mapperName = mapperNode.getName();
        this.builderName = builderNode.getName();
        PrecompiledMethod dynamic = new PrecompiledMethod(mapperNode.getName(), mapperNode.getResult(), mapperNode.getType());
        parseNode(mapperNode.getNodes(), mapperNode.getType(), dynamic);
        return dynamic;
    }

    public void parseNode(List<XMLNode> xmlNodes, String type, PrecompiledMethod dynamic) {
        for (XMLNode node : xmlNodes) {

            //
            // text
            //
            if (ProvideConstant.TEXT.equals(node.getName())) {
                if (node.getParent() != null
                        && node.getParent().getName().equals(ProvideConstant.IF)) {
                    addByIf(node, dynamic);
                } else {
                    dynamic.append(ProvideConstant.sqlAppendProcess(node.getContent()));
                }
                continue;
            }

            //
            // choose
            //
            if (ProvideConstant.CHOOSE.equals(node.getName())) {
                parseNode(node.getChildren(), type, dynamic);
                continue;
            }

            //
            // if
            //
            if (ProvideConstant.IF.equals(node.getName())) {
                List<TokenValue> test = testProcess(node.getAttribute(ProvideConstant.IF_TEST));
                String if_test = buildTest(test);
                node.addAttribute(ProvideConstant.IF_TEST, if_test);
                parseNode(node.getChildren(), type, dynamic);
                continue;
            }

            //
            // oneness
            //
            if (ProvideConstant.ONENESS.equals(node.getName())) {
                if (ProvideConstant.IF.equals(node.getParent().getName())) {
                    addByIf(node, dynamic);
                }
                continue;
            }

            //
            // foreach
            //
            if (ProvideConstant.FOREACH.equals(node.getName())) {
                String collections = node.getAttribute(ProvideConstant.COLLECTIONS);
                if (StringUtils.isEmpty(collections)) {
                    throw new ExpressionException("tag: foreach attribute collections cannot null. in mapper " + builderName + " " + mapperName);
                }
                String indexTemp = node.getAttribute(ProvideConstant.INDEX);
                String index = StringUtils.isEmpty(indexTemp) ? "index" : indexTemp;
                String itemTemp = node.getAttribute(ProvideConstant.ITEM);
                String item = StringUtils.isEmpty(itemTemp) ? "item" : itemTemp;
                dynamic.append("java.util.List " + collections + " = (java.util.List) " + ProvideConstant.getMapValue(collections));
                dynamic.append("for(");
                dynamic.append(StringUtils.format("int {} = 0,", index));
                dynamic.append(StringUtils.format("len = {}.size();", collections));
                dynamic.append(StringUtils.format("{} < len;", index));
                dynamic.append(StringUtils.format("{}++", index));
                dynamic.append("){");
                //
                // 第一个转义是需要强转的对象
                // 第二个转义是命名
                // 第三个转义是需要强转的对象
                // 第四个转义是list
                // 第五个转义为当前for循环索引
                //
                dynamic.append(StringUtils.format("java.lang.Object {} = {}.get({});", item, collections, index));
                parseNode(node.getChildren(), type, dynamic);
                dynamic.append("}");
                continue;
            }

            //
            // parameter
            //
            if (ProvideConstant.PARAMETER.equals(node.getName())) {
                List<String> parameters = new ArrayList<>();
                for (String parameter : node.getContent().split(",")) {
                    checkParameterFormat(parameter);
                    parameter = parameter.replaceAll("\\{", "");
                    parameter = parameter.replaceAll("\\}", "");
                    String value = "";
                    if (!parameter.contains(".")) {
                        value = parameter;
                    } else {
                        value = ProvideConstant.getMemberValue(parameter);
                    }
                    parameters.add(value);
                }
                dynamic.append(ProvideConstant.addArrayParameter(parameters));
                continue;
            }

        }
    }

    /**
     * 检查参数格式是否合法
     * 参数检查前两个字符与后两个字符是否为{{}},如果不是则报错
     *
     * @param parameter 参数,例如：{{parameter}}
     */
    private void checkParameterFormat(String parameter) {
        int length = parameter.length();
        if (!"{".equals(parameter.substring(0, 1))
                || !"{".equals(parameter.substring(1, 2))
                || !"}".equals(parameter.substring(length - 1, length))
                || !"}".equals(parameter.substring(length - 2, length - 1))) {
            throw new ExpressionException("parameter '" + parameter + "' format error. correct format is '{{parameterName}}'");
        }
    }

    /**
     * 添加if
     *
     * @param node
     * @param dynamic
     */
    private void addByIf(XMLNode node, PrecompiledMethod dynamic) {
        XMLNode parent = node.getParent();
        String content = node.getContent();
        String test = parent.getAttribute(ProvideConstant.IF_TEST);
        dynamic.append("if");
        dynamic.append("(");
        dynamic.append(test.replaceAll("\\$req", getParamsSelect(content)));
        dynamic.append(")");
        dynamic.append("{");
        dynamic.append(ProvideConstant.sqlAppendProcess(content));
        dynamic.append("}");

        // 如果当前节点是oneness,就从父节点查找匹配的else
        if (ProvideConstant.ONENESS.equals(node.getName())) {
            XMLNode chooseParent = node.getParent().getParent();
            if (chooseParent != null) {
                List<XMLNode> nodes = chooseParent.getChildren();
                if (nodes.size() == 2) {
                    XMLNode elseNode = nodes.get(1);
                    List<XMLNode> elseChildren = elseNode.getChildren();
                    String attributeIdValue = node.getAttribute(ProvideConstant.ONENESS_ATTRIBUTE_KEY);
                    for (XMLNode child : elseChildren) {
                        if (attributeIdValue.equals(child.getAttribute(ProvideConstant.ONENESS_ATTRIBUTE_KEY))) {
                            addByElse(child.getContent(), dynamic);
                        }
                    }
                }
            }
        } else if (ProvideConstant.TEXT.equals(node.getName())) {
            // 如果是文本那么也去父节点查找看看有妹有else
            XMLNode choose = node.getParent().getParent();
            if (choose != null && choose.getChildren().size() > 1) {
                XMLNode elseNode = choose.getChildren().get(1);
                List<XMLNode> elseChildren = elseNode.getChildren();
                if (elseChildren != null && !elseChildren.isEmpty()) {
                    addByElse(elseChildren.get(0).getContent(), dynamic);
                }
            }
        } else {
            throw new ExpressionException("unknown label '" + node.getName() + "' in mapper " + builderName + " " + mapperName);
        }

    }

    /**
     * 添加else
     *
     * @param content
     * @param dynamic
     */
    private void addByElse(String content, PrecompiledMethod dynamic) {
        dynamic.append("else");
        dynamic.append("{");
        dynamic.append(ProvideConstant.sqlAppendProcess(content));
        dynamic.append("}");
    }

    /**
     * 构建if语句
     *
     * @return
     */
    private String buildTest(List<TokenValue> test) {
        StringBuilder builder = new StringBuilder();
        for (TokenValue token : test) {
            if (token.getToken() == Token.IDEN) {
                builder.append(
                        StringUtils.format("(#{}#) {}.get(\"{}\")",
                                token.getValue(), ProvideConstant.PARAMS_MAP, token.getValue())
                );
            } else {
                builder.append(token.getValue());
            }

        }
        return builder.toString();
    }

    /**
     * 获取多个参数下被选择的标签
     *
     * @param text
     * @return
     */
    private String getParamsSelect(String text) {
        String result = "";
        Pattern pattern = Pattern.compile("\\{" + ProvideConstant.PARAMETER_SELECT + "\\{(.*?)}}");
        Matcher matcher = pattern.matcher(text);
        int count = 0; // 如果count > 1,那么抛出异常，选择项只能存在一个
        while (matcher.find()) {
            if (count >= 1) {
                throw new DynamicSQLException("tag: parameters '" + ProvideConstant.PARAMETER_SELECT + "' can only one");
            }
            result = matcher.group(1);
            count++;
        }
        if (count == 1) return result;
        count = 0; // reset
        // 如果count为0那么查找{{parameter}}
        Pattern pattern1 = Pattern.compile("\\{\\{(.*?)}}");
        Matcher matcher1 = pattern1.matcher(text);
        while (matcher1.find()) {
            if (count >= 1) {
                throw new DynamicSQLException("tag: multiple parameter need '" + ProvideConstant.PARAMETER_SELECT
                        + "' in mapper:"+builderName+" : "+mapperName);
            }
            result = matcher1.group(1);
            count++;
        }
        return result;
    }

    /**
     * 将test中的内容解析成Token
     *
     * @param test
     * @return
     */
    public List<TokenValue> testProcess(String test) {
        boolean isString = false;
        // test = test.replaceAll("'", "\"");
        test = test.replaceAll(" or ", " || ");
        test = test.replaceAll(" and ", " && ");
        test = test.replaceAll(" ", "");
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
                    i++;
                    char value = charArray[i];
                    if (value == '=') {
                        if (!StringUtils.isEmpty(builder.toString())) {
                            tokens.add(TokenValue.buildToken(Token.IDEN, builder.toString()));
                            clear(builder);
                        }
                        tokens.add(TokenValue.buildToken(Token.LE, Token.OP, "<="));
                    } else {
                        if (!StringUtils.isEmpty(builder.toString())) {
                            if (value == '\'') {
                                tokens.add(TokenValue.buildToken(Token.IDEN, builder.toString()));
                                clear(builder);
                                builder.append("\"");
                                isString = true;
                            } else {
                                tokens.add(TokenValue.buildToken(Token.IDEN, builder.toString()));
                                clear(builder);
                                builder.append(value);
                            }
                        }
                        tokens.add(TokenValue.buildToken(Token.LT, Token.OP, "<"));
                    }
                    break;
                }
                case '>': {
                    i++;
                    char value = charArray[i];
                    if (value == '=') {
                        if (!StringUtils.isEmpty(builder.toString())) {
                            tokens.add(TokenValue.buildToken(Token.IDEN, builder.toString()));
                            clear(builder);
                        }
                        tokens.add(TokenValue.buildToken(Token.GE, Token.OP, ">="));
                    } else {
                        if (!StringUtils.isEmpty(builder.toString())) {
                            if (value == '\'') {
                                tokens.add(TokenValue.buildToken(Token.IDEN, builder.toString()));
                                clear(builder);
                                builder.append("\"");
                                isString = true;
                            } else {
                                tokens.add(TokenValue.buildToken(Token.IDEN, builder.toString()));
                                clear(builder);
                                builder.append(value);
                            }
                        }
                        tokens.add(TokenValue.buildToken(Token.GT, Token.OP, ">"));
                    }
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
        String value = builder.toString();
        if (!StringUtils.isEmpty(value)) {
            tokens.add(TokenValue.buildToken(Token.IDEN, builder.toString()));
        }
        clear(builder);
        return tokens;
    }

    private void clear(StringBuilder builder) {
        builder.delete(0, builder.length());
    }


}
