package org.laniakeamly.poseidon.framework.sql.xml.parser;

import lombok.Getter;
import lombok.Setter;
import org.jdom2.Content;
import org.jdom2.Element;
import org.laniakeamly.poseidon.framework.exception.runtime.ExpressionException;
import org.laniakeamly.poseidon.framework.sql.xml.ProvideConstant;
import org.laniakeamly.poseidon.framework.sql.xml.node.XMLNode;
import org.laniakeamly.poseidon.framework.tools.StringUtils;

import java.util.List;

/**
 * xml mapper下的标签解析
 * Copyright: Create by TianSheng on 2019/12/17 0:17
 */
@SuppressWarnings("SpellCheckingInspection")
public class MapperLabelParser implements MapperLabelParserService {

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
    public XMLNode text(Content content) {
        return null;
    }

    @Override
    public XMLNode ifOrEels(Element element) {
        String ieName = element.getName();
        XMLNode ieNode = new XMLNode(ieName);
        if (ProvideConstant.IF.equals(ieName)) {
            String test = util.getIfLabelTestAttribute(element);
            if (StringUtils.isEmpty(test))
                throw new ExpressionException("tag: if label attribute test content cannot null. in mapper "
                +currentBuilder + " : "+currentMapper);
            ieNode.addAttribute(ProvideConstant.IF_TEST, test);
        }
        List<Content> conditions = element.getContent();
        int onenessCount = 0;
        for (Content condition : conditions) {
            // 文本
            if (condition.getCType() == Content.CType.Text) {
                String text = util.trim(condition.getValue());
                if (!StringUtils.isEmpty(text)) {
                    ieNode.addChild(new XMLNode(ProvideConstant.TEXT, text));
                }
                continue;
            }
            // 标签
            if (condition.getCType() == Content.CType.Element) {
                Element onenessElement = ((Element) condition);
                XMLNode oneness = new XMLNode(onenessElement.getName(), util.trim(onenessElement.getValue()));
                oneness.addAttribute(ProvideConstant.ONENESS_ATTRIBUTE_KEY,String.valueOf(onenessCount));
                onenessCount++;
                ieNode.addChild(oneness);
            }
        }
        return ieNode;
    }

    @Override
    public XMLNode choose(Element element) {
        List<Element> chooseChildren = element.getChildren();
        grammarCheck.chooseCheck(chooseChildren, currentBuilder, currentMapper);
        String ename = element.getName();
        XMLNode chooseNode = new XMLNode(ename);
        for (Element child : chooseChildren) {
            chooseNode.addChild(ifOrEels(child));
        }
        return chooseNode;
    }

    @Override
    public XMLNode foreach(Element element) {
        XMLNode eachNode = new XMLNode(element.getName());
        eachNode.addAttribute(ProvideConstant.ITEM,element.getAttributeValue(ProvideConstant.ITEM));
        eachNode.addAttribute(ProvideConstant.INDEX,element.getAttributeValue(ProvideConstant.INDEX));
        eachNode.addAttribute(ProvideConstant.COLLECTIONS,element.getAttributeValue(ProvideConstant.COLLECTIONS));
        return eachNode;
    }
}
