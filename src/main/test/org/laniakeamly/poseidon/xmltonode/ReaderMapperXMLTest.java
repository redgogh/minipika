package org.laniakeamly.poseidon.xmltonode;

import org.junit.Test;
import org.laniakeamly.poseidon.framework.sql.xml.node.XMLMapperNode;
import org.laniakeamly.poseidon.framework.sql.xml.parser.ReaderMapperXML;

import java.util.List;

/**
 * Create by 2BKeyboard on 2019/12/20 20:49
 */
public class ReaderMapperXMLTest {

    @Test
    public void read() throws Exception {
        ReaderMapperXML readerMapperXML = new ReaderMapperXML();
        List<XMLMapperNode> xmlMapperNodes = readerMapperXML.parseXML();
        System.out.println();
    }

}
