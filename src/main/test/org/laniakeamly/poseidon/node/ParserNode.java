package org.laniakeamly.poseidon.node;

import org.junit.Test;
import org.laniakeamly.recycle.build.ParserMapperNode;
import org.laniakeamly.recycle.build.PrecompiledClass;

import java.util.Map;

/**
 * Create by 2BKeyboard on 2019/12/21 15:08
 */
public class ParserNode {

    @Test
    public void test(){
        ParserMapperNode parserMapperNode = new ParserMapperNode();
        Map<String, PrecompiledClass> stringPrecompiledClassMap = parserMapperNode.readBuilderNode();
        System.out.println();
    }

}
