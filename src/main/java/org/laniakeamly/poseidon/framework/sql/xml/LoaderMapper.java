package org.laniakeamly.poseidon.framework.sql.xml;

import org.laniakeamly.poseidon.framework.sql.xml.build.ParserBuilderNode;

/**
 * 加载mapper
 * 解析、生成等方法都在这个调用并返回
 * Create by 2BKeyboard on 2019/12/17 18:14
 */
public class LoaderMapper {

    private ParserBuilderNode parserBuilderNode = new ParserBuilderNode();

    public void load() throws Exception {
        parserBuilderNode.readBuilderNode();
    }

}
