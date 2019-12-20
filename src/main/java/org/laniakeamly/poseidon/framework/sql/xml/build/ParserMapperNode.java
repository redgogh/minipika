package org.laniakeamly.poseidon.framework.sql.xml.build;

import org.laniakeamly.poseidon.framework.sql.xml.node.XMLMapperNode;
import org.laniakeamly.poseidon.framework.sql.xml.node.XMLCrudNode;
import org.laniakeamly.poseidon.framework.sql.xml.parser.ReaderMapperXML;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 解析节点
 * Create by 2BKeyboard on 2019/12/17 17:49
 */
public class ParserMapperNode {

    private ParserCrudNode parseMapper = new ParserCrudNode();

    public Map<String,PrecompiledClass> readBuilderNode() {
        try {
            ReaderMapperXML readerBuilderXML = new ReaderMapperXML();
            List<XMLMapperNode> xmlBuilderNode = readerBuilderXML.parseXML();
            Map<String,PrecompiledClass> classes = new HashMap<>();
            for (XMLMapperNode mapperNode : xmlBuilderNode) {
                PrecompiledClass dc = new PrecompiledClass(mapperNode.getName());
                for (XMLCrudNode xmlCrudNode : mapperNode.getCurds()) {
                    PrecompiledMethod pm = parseMapper.parse(xmlCrudNode, mapperNode);
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
