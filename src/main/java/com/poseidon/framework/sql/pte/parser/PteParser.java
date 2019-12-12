package com.poseidon.framework.sql.pte.parser;

import com.poseidon.framework.tools.PteString;

import java.util.List;

/**
 * 语法解析器
 * Create by 2BKeyboard on 2019/12/11 18:12
 */
public class PteParser {

    public static void main(String[] args) {
        PteParser pte = new PteParser();
        pte.parser();
    }

    public void parser() {
        PreProcess pre = new PreProcess();
        List<PteString> pteStrings = pre.readCode();
        ParserToken parserToken = new ParserToken();
        parserToken.getBuilderToken(pteStrings);
    }

    /**
     * 将sql模板转换为可执行SQL
     *
     * @param tokens 解析出来的builder tokens
     */
    public void conversionExecuteableSQL(List<PteBuilderToken> tokens) {

    }


}
