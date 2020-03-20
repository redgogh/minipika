package org.raniaia.poseidon.framework.sql.xml.token;

import lombok.Data;
import org.raniaia.poseidon.framework.provide.PoseidonProvideConstant;
import org.raniaia.poseidon.framework.tools.StringUtils;

/**
 * Copyright: Create by tiansheng on 2019/12/16 11:05
 */
@Data
public class TokenValue {

    private Token root;
    private Token token;
    private String value;

    public TokenValue(Token token, Token root, String value) {
        this.root = root;
        this.token = token;
        this.value = value;
    }

    public static TokenValue buildToken(Token key, String value) {
        if (key == Token.IDEN) {
            if (StringUtils.isNumber(value)) {
                return buildToken(Token.STRING, Token.BASIC, value);
            } else if (value.substring(0, 1).equals("\"")
                    && value.substring(value.length() - 1).equals("\"")) {
                return buildToken(Token.STRING, Token.BASIC, value);
            } else {
                if(PoseidonProvideConstant.NULL.equals(value)){
                    return buildToken(Token.NULL, key, value);
                }
                return buildToken(key, key, value);
            }
        }
        return buildToken(key, key, value);
    }

    public static TokenValue buildToken(Token key, Token root, String value) {
        return new TokenValue(key, root, value);
    }

}
