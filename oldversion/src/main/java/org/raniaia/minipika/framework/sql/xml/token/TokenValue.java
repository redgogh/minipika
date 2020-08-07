package org.jiakesiws.minipika.framework.sql.xml.token;

/* ************************************************************************
 *
 * Copyright (C) 2020 2B键盘 All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ************************************************************************/

/*
 * Creates on 2019/12/16.
 */

import lombok.Data;
import org.jiakesiws.minipika.framework.provide.ProvideVar;
import org.jiakesiws.minipika.framework.tools.StringUtils;

/**
 * @author 2B键盘
 * @email jiakesiws@gmail.com
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
                if(ProvideVar.NULL.equals(value)){
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
