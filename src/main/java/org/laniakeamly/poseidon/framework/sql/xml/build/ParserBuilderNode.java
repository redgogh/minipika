package org.laniakeamly.poseidon.framework.sql.xml.build;

import org.laniakeamly.poseidon.framework.sql.xml.node.XMLBuilderNode;
import org.laniakeamly.poseidon.framework.sql.xml.node.XMLMapperNode;

import java.util.List;

/**
 * 解析节点
 * Create by 2BKeyboard on 2019/12/17 17:49
 */
public class ParserBuilderNode {

    private ParserMapperNode parseMapper = new ParserMapperNode();

    public void readBuilderNode(List<XMLBuilderNode> xmlBuilderNode){
        for (XMLBuilderNode builderNode : xmlBuilderNode) {
            for (XMLMapperNode mapperNode : builderNode.getMappers()){
                parseMapper.parse(mapperNode);
            }
        }
    }

}
