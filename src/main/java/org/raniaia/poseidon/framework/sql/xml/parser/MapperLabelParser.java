package org.raniaia.poseidon.framework.sql.xml.parser;

import lombok.Getter;
import lombok.Setter;
import org.jdom2.Content;
import org.jdom2.Element;
import org.raniaia.poseidon.framework.exception.runtime.ExpressionException;
import org.raniaia.poseidon.framework.provide.PoseidonProvideConstant;
import org.raniaia.poseidon.framework.sql.xml.node.XMLNode;
import org.raniaia.poseidon.framework.tools.StringUtils;

import java.util.List;

/**
 * xml mapper下的标签解析
 * Copyright: Create by tiansheng on 2019/12/17 0:17
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
        if (PoseidonProvideConstant.IF.equals(ieName)) {
            String test = util.getIfLabelTestAttribute(element);
            if (StringUtils.isEmpty(test))
                throw new ExpressionException("tag: if label attribute test content cannot null. in mapper "
                +currentBuilder + " : "+currentMapper);
            ieNode.addAttribute(PoseidonProvideConstant.IF_TEST, test);
        }
        List<Content> conditions = element.getContent();
        int condCount = 0;
        for (Content condition : conditions) {
            // 文本
            if (condition.getCType() == Content.CType.Text) {
                String text = util.trim(condition.getValue());
                if (!StringUtils.isEmpty(text)) {
                    ieNode.addChild(new XMLNode(PoseidonProvideConstant.TEXT, text));
                }
                continue;
            }
            // 标签
            if (condition.getCType() == Content.CType.Element) {
                Element condElement = ((Element) condition);
                XMLNode cond = new XMLNode(condElement.getName(), util.trim(condElement.getValue()));
                cond.addAttribute(PoseidonProvideConstant.COND_ATTRIBUTE_KEY,String.valueOf(condCount));
                condCount++;
                ieNode.addChild(cond);
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
        eachNode.addAttribute(PoseidonProvideConstant.ITEM,element.getAttributeValue(PoseidonProvideConstant.ITEM));
        eachNode.addAttribute(PoseidonProvideConstant.INDEX,element.getAttributeValue(PoseidonProvideConstant.INDEX));
        eachNode.addAttribute(PoseidonProvideConstant.COLLECTIONS,element.getAttributeValue(PoseidonProvideConstant.COLLECTIONS));
        return eachNode;
    }
}
