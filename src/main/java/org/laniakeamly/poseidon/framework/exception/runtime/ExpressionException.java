package org.laniakeamly.poseidon.framework.exception.runtime;

/**
 * Copyright: Create by 2BKeyboard on 2019/12/7 1:10
 */
public class ExpressionException extends RuntimeException {

    public ExpressionException() {
    }

    public ExpressionException(String message) {
        super(message);
    }

    public ExpressionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpressionException(Throwable cause) {
        super(cause);
    }

    public ExpressionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
