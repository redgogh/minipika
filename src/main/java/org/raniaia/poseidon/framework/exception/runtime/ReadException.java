package org.raniaia.poseidon.framework.exception.runtime;

/**
 * Copyright: Create by tiansheng on 2019/12/3 23:30
 */
public class
ReadException extends RuntimeException {

    public ReadException() {
    }

    public ReadException(String message) {
        super(message);
    }

    public ReadException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReadException(Throwable cause) {
        super(cause);
    }

    public ReadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
