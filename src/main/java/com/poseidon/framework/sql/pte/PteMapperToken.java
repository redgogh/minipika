package com.poseidon.framework.sql.pte;

import com.poseidon.framework.tools.PteString;
import lombok.Getter;
import lombok.Setter;

/**
 * builder下的Mapper token
 * Create by 2BKeyboard on 2019/12/11 17:43
 */
@Getter
@Setter
public class PteMapperToken {

    private String tokenKey;
    private PteString tokenValue;

    public PteMapperToken(){}

    public PteMapperToken(String tokenKey, PteString tokenValue) {
        this.tokenKey = tokenKey;
        this.tokenValue = tokenValue;
    }

}
