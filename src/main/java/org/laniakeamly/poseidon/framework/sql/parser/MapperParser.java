package org.laniakeamly.poseidon.framework.sql.parser;

import lombok.Getter;
import lombok.Setter;
import org.jdom2.Content;
import org.jdom2.Element;
import org.laniakeamly.poseidon.framework.exception.ExpressionException;
import org.laniakeamly.poseidon.framework.sql.build.Node;
import org.laniakeamly.poseidon.framework.tools.StringUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * xml mapper下的标签解析
 * Create by 2BKeyboard on 2019/12/17 0:17
 */
@SuppressWarnings("SpellCheckingInspection")
public class MapperParser implements XMLParserService {

    // 解析工具类
    private XMLParserUtils util = new XMLParserUtils();

    // 语法错误检测
    private GrammarCheck grammarCheck = new GrammarCheck();

    @Setter
    @Getter
    private String currentBuilder;

    @Setter
    @Getter
    private String currentMapper;

    @Override
    public Node text(Content content) {
        return null;
    }

    @Override
    public Node ifOrEels(Element element) {
        String ieName = element.getName();
        Node ieNode = new Node(ieName);
        if ("if".equals(ieName)) {
            String test = util.getIfLabelTestAttribute(element);
            if (StringUtils.isEmpty(test))
                throw new ExpressionException("tag: if label attribute test content cannot null.");
            ieNode.addAttribute("test", test);
        }
        List<Content> conditions = element.getContent();
        for (Content condition : conditions) {
            // 文本
            if (condition.getCType() == Content.CType.Text) {
                String text = util.trim(condition.getValue());
                if (!StringUtils.isEmpty(text)) {
                    ieNode.addChild(new Node("text", text));
                }
                continue;
            }
            // 标签
            if (condition.getCType() == Content.CType.Element) {
                Element cond = ((Element) condition);
                ieNode.addChild(new Node(cond.getName(), util.trim(cond.getValue())));
            }
        }
        return ieNode;
    }

    @Override
    public Node choose(Element element) {
        List<Element> chooseChildren = element.getChildren();
        grammarCheck.chooseCheck(chooseChildren, currentBuilder, currentMapper);
        String ename = element.getName();
        Node chooseNode = new Node(ename);
        for (Element child : chooseChildren) {
            chooseNode.addChild(ifOrEels(child));
        }
        return chooseNode;
    }

}
