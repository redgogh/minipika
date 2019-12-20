package org.laniakeamly.poseidon.framework.container;

import org.laniakeamly.poseidon.framework.sql.xml.build.ParserMapperNode;
import org.laniakeamly.poseidon.framework.sql.xml.build.PrecompiledClass;
import org.laniakeamly.poseidon.framework.tools.StringUtils;

import java.util.Map;

/**
 * 预编译代码容器
 * Create by 2BKeyboard on 2019/12/19 11:24
 */
public class Precompiled implements Container {

    private ParserMapperNode parserBuilderNode = new ParserMapperNode();

    private Map<String, PrecompiledClass> map;

    public Precompiled(){
        map = parserBuilderNode.readBuilderNode();
    }

    @Override
    public PrecompiledClass getValue(String name) {
        PrecompiledClass precompiledClass = map.get(name);
        if(precompiledClass == null){
            throw new NullPointerException(StringUtils.format("{} builder not found please check your xml",name));
        }
        return precompiledClass;
    }

}
