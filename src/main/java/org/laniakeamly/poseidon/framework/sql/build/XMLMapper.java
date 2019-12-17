package org.laniakeamly.poseidon.framework.sql.build;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Create by 2BKeyboard on 2019/12/17 15:06
 */
@Getter
@Setter
public class XMLMapper {

    private String name;
    private List<Node> nodes = new LinkedList<>();

    public void addNode(Node node){
        nodes.add(node);
    }

}
