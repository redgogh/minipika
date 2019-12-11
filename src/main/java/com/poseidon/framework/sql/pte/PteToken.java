package com.poseidon.framework.sql.pte;

import lombok.Getter;
import lombok.Setter;

/**
 * Create by 2BKeyboard on 2019/12/11 17:43
 */
@Getter
@Setter
public class PteToken {

    public PteToken(){}

    public PteToken(String tokenKey, String tokenValue) {
        this.tokenKey = tokenKey;
        this.tokenValue = tokenValue;
    }

    private String tokenKey;
    private String tokenValue;

}
