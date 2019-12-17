package org.laniakeamly.poseidon.framework.sql.xml.node;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;

/**
 * Create by 2BKeyboard on 2019/12/17 14:57
 */
@Getter
@Setter
public class XMLBuilderNode {

    private String name;
    private LinkedList<XMLMapperNode> mappers
            = new LinkedList<>();

    public XMLBuilderNode(String name) {
        this.name = name;
    }

    public void addMapper(XMLMapperNode mapper){
        mappers.add(mapper);
    }

}
