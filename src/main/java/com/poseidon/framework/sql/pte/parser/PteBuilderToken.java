package com.poseidon.framework.sql.pte.parser;

import com.poseidon.framework.tools.PteString;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * builder token 负责存放每个builder下的mapper
 * Create by 2BKeyboard on 2019/12/11 17:47
 */
public class PteBuilderToken {

    //
    // builderValue就像是类名,像这样builder builderValue {}
    //
    private String builderValue;

    @Getter
    private List<PteMapperToken> mapperTokens =new ArrayList<>();

    public PteBuilderToken(){}

    public PteBuilderToken(String builderValue){
        this.builderValue = builderValue;
    }

    public void builderToken(String tokenKey, PteString tokenValue){
        PteMapperToken token = new PteMapperToken();
        token.setTokenKey(tokenKey);
        token.setTokenValue(tokenValue);
        mapperTokens.add(token);
    }

    public void builderToken(PteMapperToken builderValue){
        mapperTokens.add(builderValue);
    }

}
