package org.laniakeamly.poseidon.framework.sql.xml.node;

import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by 2BKeyboard on 2019/12/17 14:57
 */
@Getter
public class XMLNode {

    private String name;
    private String content;
    private List<XMLNode> children;
    private Map<String,String> attributes = new LinkedHashMap<>(2);

    public XMLNode(){}

    public XMLNode(String name) {
        this.name = name;
    }

    public XMLNode(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public void addAttribute(String key,String value){
        attributes.put(key, value);
    }

    public String getAttribute(String name){
        return attributes.get(name);
    }

    public void addChild(XMLNode node){
       if(children == null){
           children = new ArrayList<>();
       }
        children.add(node);
    }

}
