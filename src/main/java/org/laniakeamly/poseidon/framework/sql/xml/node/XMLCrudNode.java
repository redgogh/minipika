package org.laniakeamly.poseidon.framework.sql.xml.node;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

/**
 * Create by 2BKeyboard on 2019/12/17 15:06
 */
@Getter
@Setter
public class XMLCrudNode {

    private String name;
    private String type;
    private String result;

    private List<XMLNode> nodes = new LinkedList<>();

    public void addNode(XMLNode node){
        nodes.add(node);
    }

}
