package org.laniakeamly.poseidon.framework.exception.runtime;

/**
 * Copyright: Create by TianSheng on 2019/12/10 10:56
 */
public class BeansManagerException extends RuntimeException {

    public BeansManagerException() {
    }

    public BeansManagerException(String message) {
        super(message);
    }

    public BeansManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeansManagerException(Throwable cause) {
        super(cause);
    }

    public BeansManagerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
