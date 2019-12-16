package org.maitreya.poseidon.framework.sql.builder;

import org.maitreya.poseidon.framework.exception.ExpressionException;

/**
 *
 * Create by 2BKeyboard on 2019/12/16 21:35
 */
public class GrammarCheck {

    public void test(Token var1, Token var2, Token op) {

        if (var1 == Token.STRING || var2 == Token.STRING) {
            if (op != Token.EQ || op != Token.NE) {
                throw new ExpressionException("");
            }
        }

    }

}
