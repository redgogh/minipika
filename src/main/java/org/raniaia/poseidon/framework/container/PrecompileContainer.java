package org.raniaia.poseidon.framework.container;

import org.raniaia.poseidon.framework.sql.xml.build.ParserMapperNode;
import org.raniaia.poseidon.framework.sql.xml.build.PrecompiledClass;

import java.util.Map;

/**
 * Copyright: Create by TianSheng on 2019/12/23 10:59
 */
public class PrecompileContainer {

    private static PrecompileContainer pc;
    private static Map<String, PrecompiledClass> precompiledClassMap;

    private PrecompileContainer(){
        ParserMapperNode parserMapperNode = new ParserMapperNode();
        precompiledClassMap = parserMapperNode.readBuilderNode();
    }

    public static PrecompileContainer getContainer(){
        if(pc == null){
            pc = new PrecompileContainer();
        }
        return pc;
    }

    public PrecompiledClass getValue(String name) {
        return precompiledClassMap.get(name);
    }

}
