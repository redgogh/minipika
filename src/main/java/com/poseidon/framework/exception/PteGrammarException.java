package com.poseidon.framework.exception;

/**
 * pte文件语法异常
 * Create by 2BKeyboard on 2019/12/12 18:46
 */
public class PteGrammarException extends RuntimeException {

    public PteGrammarException() {
    }

    public PteGrammarException(String message) {
        super(message);
    }

    public PteGrammarException(String message, Throwable cause) {
        super(message, cause);
    }

    public PteGrammarException(Throwable cause) {
        super(cause);
    }

    public PteGrammarException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
