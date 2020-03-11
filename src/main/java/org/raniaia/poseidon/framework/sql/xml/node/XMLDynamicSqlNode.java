package org.raniaia.poseidon.framework.sql.xml.node;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

/**
 * Copyright: Create by TianSheng on 2019/12/17 15:06
 */
@Getter
@Setter
public class XMLDynamicSqlNode {

    private String name;
    private String type;
    private String result;

    private List<XMLNode> nodes = new LinkedList<>();

    public void addNode(XMLNode node){
        nodes.add(node);
    }

}
