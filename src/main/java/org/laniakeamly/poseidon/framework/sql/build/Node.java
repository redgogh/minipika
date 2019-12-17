package org.laniakeamly.poseidon.framework.sql.build;

import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by 2BKeyboard on 2019/12/17 14:57
 */
@Getter
public class Node {

    private String name;
    private String content;
    private List<Node> children;
    private Map<String,String> attributes = new LinkedHashMap<>(2);

    public Node(){}

    public Node(String name) {
        this.name = name;
    }

    public Node(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public void addAttribute(String key,String value){
        attributes.put(key, value);
    }

    public void addChild(Node node){
       if(children == null){
           children = new ArrayList<>();
       }
        children.add(node);
    }

}
