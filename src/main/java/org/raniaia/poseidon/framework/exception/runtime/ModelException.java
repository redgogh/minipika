package org.raniaia.poseidon.framework.exception.runtime;

/**
 * Model异常
 * Copyright: Create by tiansheng on 2020/1/15 1:57
 */
public class ModelException extends RuntimeException {

    public ModelException() {
    }

    public ModelException(String message) {
        super(message);
    }

    public ModelException(String message, Throwable cause) {
        super(message, cause);
    }

    public ModelException(Throwable cause) {
        super(cause);
    }

    public ModelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
