package com.poseidon.framework.sql.pte;

import java.util.List;

/**
 * 语法解析器
 * Create by 2BKeyboard on 2019/12/11 18:12
 */
public class PteParser {

    public static void main(String[] args){

        PteParser pte = new PteParser();
        pte.parser();

    }

    public void parser(){
        PreProcess pre = new PreProcess();
        ParserToken parserToken = new ParserToken(pre.readCode());
        List<PteBuilderToken> tokens = parserToken.getBuilderToken();
        traversalToken(tokens);
    }

    public void traversalToken(List<PteBuilderToken> tokens){

        for(PteBuilderToken builderToken : tokens){
            List<PteMapperToken> mapperTokens = builderToken.getMapperTokens();
            for(PteMapperToken mapperToken : mapperTokens){

                System.out.println();
                System.out.println();
                System.out.println();
                System.out.println("----------------------------------------------------------------------------------");
                System.err.println(mapperToken.getTokenValue());
                System.out.println("----------------------------------------------------------------------------------");
                System.out.println();
                System.out.println();
                System.out.println();
            }
        }

    }


}
