package com.poseidon.framework.sql.pte;

import java.util.List;

/**
 * Create by 2BKeyboard on 2019/12/11 18:12
 */
public class PteParser {

    public static void main(String[] args){
        PreProcess pre = new PreProcess();
        ParserToken parserToken = new ParserToken(pre.readCode());
        List<PteBuilderToken> tokens = parserToken.getBuilderToken();



    }

}
