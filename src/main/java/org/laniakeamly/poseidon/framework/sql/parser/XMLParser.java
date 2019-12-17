package org.laniakeamly.poseidon.framework.sql.parser;

import lombok.Getter;
import lombok.Setter;
import org.jdom2.Content;
import org.jdom2.Element;
import org.laniakeamly.poseidon.framework.exception.ExpressionException;
import org.laniakeamly.poseidon.framework.tools.StringUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Create by 2BKeyboard on 2019/12/17 0:17
 */
public class XMLParser implements XMLParserService {

    // 解析工具类
    private XMLParserUtils utils = new XMLParserUtils();

    // 语法错误检测
    private GrammarCheck grammarCheck = new GrammarCheck();

    @Setter
    @Getter
    private String currentBuilder;

    @Setter
    @Getter
    private String currentMapper;

    @Override
    public String text(Content content) {
        return null;
    }

    @Override
    public String _if(Element element) {
        String test = checkTestContent(element);
        if(StringUtils.isEmpty(test))
            throw new ExpressionException("tag: if label attribute test content cannot null.");

        List<Content> conditions = element.getContent();


        return null;
    }

    @Override
    public String choose(Element element) {
        List<Element> chooseChildren = element.getChildren();
        grammarCheck.chooseCheck(chooseChildren,currentBuilder,currentMapper);
        return null;
    }

    private String checkTestContent(Element element){
        String test = element.getAttributeValue("test");
        if(StringUtils.isEmpty(test))
            throw new ExpressionException("tag: if label attribute test content cannot null.");
        return test;
    }

}
