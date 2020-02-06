package org.laniakeamly.poseidon.framework.exception.runtime;

/**
 * Copyright: Create by 2BKeyboard on 2019/12/18 0:05
 */
public class DynamicSQLException extends RuntimeException {

    public DynamicSQLException() {
    }

    public DynamicSQLException(String message) {
        super(message);
    }

    public DynamicSQLException(String message, Throwable cause) {
        super(message, cause);
    }

    public DynamicSQLException(Throwable cause) {
        super(cause);
    }

    public DynamicSQLException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
