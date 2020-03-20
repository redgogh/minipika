package org.raniaia.poseidon.framework.sql.xml.node;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;

/**
 * Copyright: Create by tiansheng on 2019/12/17 14:57
 */
@Getter
@Setter
public class XMLMapperNode {

    private String name;
    private LinkedList<XMLDynamicSqlNode> dynamicsSqlSet
            = new LinkedList<>();

    public XMLMapperNode(String name) {
        this.name = name;
    }

    public void add(XMLDynamicSqlNode mapper){
        dynamicsSqlSet.add(mapper);
    }

}
