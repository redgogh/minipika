package org.laniakeamly.poseidon.framework.sql.xml.build;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

/**
 * if else value
 * Create by 2BKeyboard on 2019/12/18 3:33
 */
public class IEValue {

    @Getter
    @Setter
    List<String> tests = new LinkedList<>();

    @Getter
    List<String> ifContent = new LinkedList<>();

    @Getter
    List<String> elseContent = new LinkedList<>();

    public void addTest(String test){
        tests.add(test);
    }
    public void addIfContent(String content){
        ifContent.add(content);
    }

    public void addElseContent(String content){
        elseContent.add(content);
    }

}
