package org.laniakea.poseidon.framework.sql.parser;

import org.jdom2.Element;
import org.laniakea.poseidon.framework.exception.BuilderXmlException;
import org.laniakea.poseidon.framework.tools.StringUtils;

import java.util.List;

/**
 * 语法错误检测
 * Create by 2BKeyboard on 2019/12/16 21:35
 */
public class GrammarCheck {

    /**
     * 检测choose标签是否满足规范
     * @param chooseChildren choose标签下的element
     * @param builderName      当前choose在哪个mapper下
     */
    public void chooseCheck(List<Element> chooseChildren, String builderName, String mapperName) {
        if (chooseChildren.size() <= 0)
            throw new BuilderXmlException("tag: choose cannot if and else label in builder " + builderName + " mapper: " + mapperName);
        Element _if = chooseChildren.get(0);
        if (chooseChildren.size() > 0 && !"if".equals(_if.getName()))
            throw new BuilderXmlException("tag: choose at least include a if label builder " + builderName + " mapper: " + mapperName);
        if (StringUtils.isEmpty(_if.getAttribute("test").getValue()))
            throw new BuilderXmlException("tag: choose in if attribute test cannot null in builder " + builderName + " mapper: " + mapperName);
    }

}
