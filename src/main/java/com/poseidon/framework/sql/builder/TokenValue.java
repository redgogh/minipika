package com.poseidon.framework.sql.builder;

import lombok.Data;

/**
 * Create by 2BKeyboard on 2019/12/16 11:05
 */
@Data
public class TokenValue {

    private TestToken token;
    private String value;

    public TokenValue(TestToken token, String value) {
        this.token = token;
        this.value = value;
    }

    public static TokenValue buildToken(TestToken key, String value) {
        return new TokenValue(key, value);
    }

}
