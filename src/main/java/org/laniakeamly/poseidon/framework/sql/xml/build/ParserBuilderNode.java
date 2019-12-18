package org.laniakeamly.poseidon.framework.sql.xml.build;

import org.laniakeamly.poseidon.framework.sql.xml.node.XMLBuilderNode;
import org.laniakeamly.poseidon.framework.sql.xml.node.XMLMapperNode;
import org.laniakeamly.poseidon.framework.sql.xml.parser.ReaderBuilderXML;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 解析节点
 * Create by 2BKeyboard on 2019/12/17 17:49
 */
public class ParserBuilderNode {

    private ParserMapperNode parseMapper = new ParserMapperNode();

    public List<DynamicClass> readBuilderNode() throws Exception {
        ReaderBuilderXML readerBuilderXML = new ReaderBuilderXML();
        List<XMLBuilderNode> xmlBuilderNode = readerBuilderXML.parseXML();
        List<DynamicClass> classes = new ArrayList<>();
        for (XMLBuilderNode builderNode : xmlBuilderNode) {
            DynamicClass dc = new DynamicClass(builderNode.getName());
            for (XMLMapperNode mapperNode : builderNode.getMappers()){
                dc.addDynamicMethod(parseMapper.parse(mapperNode,builderNode));
            }
        }
        return classes;
    }

}
