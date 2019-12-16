package com.poseidon.framework.sql.builder;

import lombok.Data;

/**
 * Create by 2BKeyboard on 2019/12/16 11:05
 */
@Data
public class TokenValue {

    private Token root;
    private Token token;
    private String value;

    public TokenValue(Token token, Token root, String value) {
        this.root =root;
        this.token = token;
        this.value = value;
    }

    public static TokenValue buildToken(Token key, String value) {
        return new TokenValue(key,null, value);
    }

    public static TokenValue buildToken(Token key, Token root, String value) {
        return new TokenValue(key,root, value);
    }

}
