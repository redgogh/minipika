package org.laniakeamly.poseidon.framework.sql.xml.build;

import org.laniakeamly.poseidon.framework.sql.ProvideConstant;
import org.laniakeamly.poseidon.framework.sql.xml.node.XMLNode;
import org.laniakeamly.poseidon.framework.sql.xml.node.XMLMapperNode;

import java.util.List;

/**
 * 将MapperNode转换成Java代码
 * Create by 2BKeyboard on 2019/12/17 17:51
 */
public class ParserMapperNode {

    public DynamicMethod parse(XMLMapperNode mapperNode) {
        DynamicMethod dynamic = new DynamicMethod(mapperNode.getName());
        parseNode(mapperNode.getNodes(), dynamic);
        return dynamic;
    }

    public void parseNode(List<XMLNode> xmlNodes, DynamicMethod dynamic) {
        for (XMLNode node : xmlNodes) {

            //
            // 文本文件
            //
            if (ProvideConstant.TEXT.equals(node.getName())) {
                dynamic.append(node.getContent());
            }

            //
            // choose标签
            //
            if (ProvideConstant.CHOOSE.equals(node.getName())) {
                parseNode(node.getChildren(), dynamic);
            }

            //
            // if标签
            //
            if (ProvideConstant.IF.equals(node.getName())) {
                StringBuilder ifstr = new StringBuilder();
            }

            //
            // else标签
            //
            if (ProvideConstant.ELSE.equals(node.getName())) {

            }

        }
    }
}
