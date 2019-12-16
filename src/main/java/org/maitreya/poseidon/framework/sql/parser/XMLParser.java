package org.maitreya.poseidon.framework.sql.parser;

import lombok.Setter;
import org.jdom2.Content;
import org.jdom2.Element;

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
    private String currentBuilder;
    @Setter
    private String currentMapper;

    @Override
    public String text(Content content) {
        return null;
    }

    @Override
    public String _if(Element element) {
        return null;
    }

    @Override
    public String choose(Element element) {
        List<Element> chooseChildren = element.getChildren();
        grammarCheck.chooseCheck(chooseChildren,currentBuilder,currentMapper);
        return null;
    }
}
