package com.poseidon.framework.sql;

import lombok.Getter;
import lombok.Setter;

/**
 * Create by 2BKeyboard on 2019/12/5 0:05
 */
public class Token {

    enum Type{TABLE}

    @Getter
    @Setter
    public Type type;

    @Getter
    @Setter
    public String value;

}
