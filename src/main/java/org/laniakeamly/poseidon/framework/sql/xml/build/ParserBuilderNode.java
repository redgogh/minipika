package org.laniakeamly.poseidon.framework.sql.xml.build;

import org.laniakeamly.poseidon.framework.sql.xml.node.XMLBuilderNode;
import org.laniakeamly.poseidon.framework.sql.xml.node.XMLMapperNode;
import org.laniakeamly.poseidon.framework.sql.xml.parser.ReaderBuilderXML;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 解析节点
 * Create by 2BKeyboard on 2019/12/17 17:49
 */
public class ParserBuilderNode {

    private ParserMapperNode parseMapper = new ParserMapperNode();

    public Map<String,PrecompiledClass> readBuilderNode() {
        try {
            ReaderBuilderXML readerBuilderXML = new ReaderBuilderXML();
            List<XMLBuilderNode> xmlBuilderNode = readerBuilderXML.parseXML();
            Map<String,PrecompiledClass> classes = new HashMap<>();
            for (XMLBuilderNode builderNode : xmlBuilderNode) {
                PrecompiledClass dc = new PrecompiledClass(builderNode.getName());
                for (XMLMapperNode mapperNode : builderNode.getMappers()) {
                    PrecompiledMethod pm = parseMapper.parse(mapperNode, builderNode);
                    dc.addPrecompiledMethod(pm);
                }
                classes.put(dc.getName(),dc);
            }
            return classes;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
