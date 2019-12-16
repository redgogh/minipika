package com.poseidon.framework.sql.builder;

import lombok.Data;

/**
 * Create by 2BKeyboard on 2019/12/16 11:05
 */
@Data
public class TokenValue {

    private TestToken root;
    private TestToken token;
    private String value;

    public TokenValue(TestToken token,TestToken root,String value) {
        this.root =root;
        this.token = token;
        this.value = value;
    }

    public static TokenValue buildToken(TestToken key,String value) {
        return new TokenValue(key,null, value);
    }

    public static TokenValue buildToken(TestToken key, TestToken root,String value) {
        return new TokenValue(key,root, value);
    }

}
