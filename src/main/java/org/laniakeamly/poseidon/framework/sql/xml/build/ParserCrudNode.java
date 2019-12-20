package org.laniakeamly.poseidon.framework.sql.xml.build;

import org.laniakeamly.poseidon.framework.exception.runtime.DynamicSQLException;
import org.laniakeamly.poseidon.framework.sql.ProvideConstant;
import org.laniakeamly.poseidon.framework.sql.xml.node.XMLMapperNode;
import org.laniakeamly.poseidon.framework.sql.xml.node.XMLNode;
import org.laniakeamly.poseidon.framework.sql.xml.node.XMLCrudNode;
import org.laniakeamly.poseidon.framework.sql.xml.token.Token;
import org.laniakeamly.poseidon.framework.sql.xml.token.TokenValue;
import org.laniakeamly.poseidon.framework.tools.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 将CrudNode转换成Java代码
 * Create by 2BKeyboard on 2019/12/17 17:51
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

    public PrecompiledMethod parse(XMLCrudNode mapperNode, XMLMapperNode builderNode) {
        mapperName = mapperNode.getName();
        builderName = builderNode.getName();
        PrecompiledMethod dynamic = new PrecompiledMethod(mapperNode.getName(), mapperNode.getResult(),mapperNode.getType());
        parseNode(mapperNode.getNodes(), mapperNode.getType(), dynamic, null, false,false);
        return dynamic;
    }

    public void parseNode(List<XMLNode> xmlNodes, String type, PrecompiledMethod dynamic, IEValue ieValue, boolean choose,boolean foreach) {
        for (XMLNode node : xmlNodes) {

            //
            // text
            //
            if (ProvideConstant.TEXT.equals(node.getName())) {
                dynamic.appendSql(node.getContent());
            }

            //
            // choose
            //
            if (ProvideConstant.CHOOSE.equals(node.getName())) {
                IEValue _ieValue = new IEValue();
                parseNode(node.getChildren(), type, dynamic, _ieValue, true,false);
                dynamic.addif(_ieValue);
                continue;
            }

            //
            // if
            //
            if (ProvideConstant.IF.equals(node.getName())) {
                List<TokenValue> values = testProcess(node.getAttribute("test"));
                for (XMLNode child : node.getChildren()) {
                    String test = buildTestContent(values, child);
                    if (ieValue != null) {
                        ieValue.addTest(test);
                    }
                    if (choose) {
                        ieValue.addIfContent(child.getContent());
                    } else {
                        IEValue _ieValue = new IEValue();
                        _ieValue.addTest(test);
                        _ieValue.addIfContent(child.getContent());
                        dynamic.addif(_ieValue);
                    }
                }
                continue;
            }

            //
            // else
            //
            if (ProvideConstant.ELSE.equals(node.getName())) {
                if (choose) {
                    for (XMLNode child : node.getChildren()) {
                        ieValue.addElseContent(child.getContent());
                    }
                }
                continue;
            }

            //
            // foreach
            //
            if (ProvideConstant.FOREACH.equals(node.getName())) {
                String item = node.getAttribute(ProvideConstant.ITEM);
                String index = node.getAttribute(ProvideConstant.INDEX);
                String collections = node.getAttribute(ProvideConstant.COLLECTIONS);
                dynamic.append(buildCycleBody(index, item, collections,type));
                parseNode(node.getChildren(), type,dynamic, null, false,true);
                dynamic.append("}");
                dynamic.append("}");
                continue;
            }

        }
    }

    /**
     * 构建循环体
     * @param index             index相当于我们平常for循环中的i
     * @param item              相当于list获取到的值
     * @param collections       集合对象
     * @return 构建好的Java代码
     */
    private String buildCycleBody(String index, String item, String collections,String type) {
        StringBuilder builder = new StringBuilder();
        String itemName = "$".concat(item);
        builder.append(
                StringUtils.format("java.util.List {} = (java.util.List) map.get(\"{}\");", collections, collections)
        );
        builder.append(StringUtils.format("if(!{}.isEmpty()){", collections)); // 如果不等于空才进行遍历
        builder.append(StringUtils.format(
                "for(int {}=0,len={}.size(); {}<len; {}++){", index, collections, index, index)
        );
        builder.append(
                StringUtils.format("#{}# {} = {}.get({});", collections, itemName, collections, index)
        );
        return builder.toString();
    }

    /**
     * 从token中构建test
     * @param values
     * @param child
     * @return
     */
    private String buildTestContent(List<TokenValue> values, XMLNode child) {
        StringBuilder builder = new StringBuilder();
        for (TokenValue tokenValue : values) {
            String value = tokenValue.getValue();
            if (tokenValue.getToken() == Token.IDEN || tokenValue.getToken() == Token.BASIC) {
                if (ProvideConstant.REQ.equals(value)) {
                    String paramName = getParam(child.getContent());
                    builder.append(StringUtils.format("(#{}#) map.get(\"{}\") ", paramName, paramName));
                    continue;
                } else {
                    builder.append(StringUtils.format("(#{}#) map.get(\"{}\") ", value, value));
                    continue;
                }
            }
            builder.append(tokenValue.getValue()).append(" ");
        }
        return builder.toString();
    }

    /**
     * 从sql中获取参数
     * 例如: username = {{username}} 那么获取到的参数就是username
     * 如果一个条件中有多个参数的话，那么必须指定使用哪个参数替代{@code $req}
     * 假设当前有这么一条sql：
     *      <code>
     *          username = {{name}} and user_age > {{userAge}}
     *      </code>
     *
     * 由于$req是代表当前if下所有的条件都是一样的，可以用$req代替
     * 所以有两个参数就必须得指定了,假设我要设置name替换$req那么就可以向以下这样写
     *      <code>
     *          username = {this:{name}} and user_age > {{userAge}}
     *      </code>
     *
     *
     * @param content
     * @return
     */
    private String getParam(String content) {
        if (StringUtils.isEmpty(content)) {
            throw new DynamicSQLException("tag: 'if' label test content if have '$req' at least one 'cond' label n builder: "
                    + builderName + " mapper: " + mapperName);
        }
        int cont = 0;
        String name = null;
        Matcher matcher = Pattern.compile("\\{this:\\{(.*?)}}").matcher(content);
        while (matcher.find()) {
            name = matcher.group(1);
            if (cont++ >= 2) break;
        }
        if (cont >= 2) {
            throw new DynamicSQLException("tag: this can only have one. "
                    + builderName + " mapper: " + mapperName);
        } else {
            matcher = Pattern.compile("\\{\\{(.*?)}}").matcher(content);
            cont = 0;
            while (matcher.find()) {
                name = matcher.group(1);
                if (cont++ > 2) break;
            }
            if (cont >= 2) {
                throw new DynamicSQLException("tag: multiple parameter need specify parameter in builder: "
                        + builderName + " mapper: " + mapperName);
            }
        }
        if (StringUtils.isEmpty(name)) {
            throw new DynamicSQLException("tag: test content if have '$req' at least one parameter in builder: "
                    + builderName + " mapper: " + mapperName);
        }
        return name;
    }

    /**
     * 将test中的内容解析成Token
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
