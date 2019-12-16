package org.maitreya.poseidon.framework.sql.parser;

import org.jdom2.Element;
import org.maitreya.poseidon.framework.exception.ExpressionException;
import org.maitreya.poseidon.framework.tools.NewlineBuilder;
import org.maitreya.poseidon.framework.tools.StringUtils;

import java.util.List;

/**
 * 解析工具类
 * Create by 2BKeyboard on 2019/12/17 0:26
 */
public class XMLParserUtils {

    /**
     * 获取test标签中的内容
     * @param element
     * @return
     */
    public String getIfLabelTestAttribute(Element element){
        String test = element.getAttributeValue("test");
        if(StringUtils.isEmpty(test))
            throw new ExpressionException("tag: if label attribute test content cannot null");
        return test;
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

}
