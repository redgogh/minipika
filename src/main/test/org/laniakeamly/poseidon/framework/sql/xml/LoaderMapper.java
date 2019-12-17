package org.laniakeamly.poseidon.framework.sql.xml;

import org.junit.Test;
import org.laniakeamly.poseidon.framework.sql.xml.build.ParserBuilderNode;
import org.laniakeamly.poseidon.framework.sql.xml.node.XMLBuilderNode;
import org.laniakeamly.poseidon.framework.sql.xml.parser.ReaderBuilderXML;

import java.util.List;

/**
 * 加载mapper
 * 解析、生成等方法都在这个调用并返回
 * Create by 2BKeyboard on 2019/12/17 18:14
 */
public class LoaderMapper {

    private ReaderBuilderXML readerBuilderXML = new ReaderBuilderXML();
    private ParserBuilderNode parserBuilderNode = new ParserBuilderNode();

    @Test
    public void load() throws Exception {
        List<XMLBuilderNode> xmlBuilderNodes = readerBuilderXML.parseXML();
        parserBuilderNode.readBuilderNode(xmlBuilderNodes);
    }

}
