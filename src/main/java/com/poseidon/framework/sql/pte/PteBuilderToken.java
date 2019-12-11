package com.poseidon.framework.sql.pte;

import java.util.List;

/**
 * Create by 2BKeyboard on 2019/12/11 17:47
 */
public class PteBuilderToken {

    //
    // builderValue就像是类名,像这样builder builderValue {}
    //
    private String builderValue;

    private List<PteToken> tokens;

    public PteBuilderToken(){}

    public PteBuilderToken(String builderValue){
        this.builderValue = builderValue;
    }

    public void builderToken(String tokenKey,String tokenValue){
        PteToken token = new PteToken();
        token.setTokenKey(tokenKey);
        token.setTokenValue(tokenValue);
        tokens.add(token);
    }

}
