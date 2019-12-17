package org.laniakeamly.poseidon.framework.sql.build;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;

/**
 * Create by 2BKeyboard on 2019/12/17 14:57
 */
@Getter
@Setter
public class XMLBuilder {

    private String name;
    private LinkedList<XMLMapper> mappers
            = new LinkedList<>();

    public XMLBuilder(String name) {
        this.name = name;
    }

    public void addMapper(XMLMapper mapper){
        mappers.add(mapper);
    }

}
