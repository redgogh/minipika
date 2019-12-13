package com.poseidon.framework.exception;

/**
 * mapper xml文件异常
 * Create by 2BKeyboard on 2019/12/12 18:46
 */
public class MapperException extends RuntimeException {

    public MapperException() {
    }

    public MapperException(String message) {
        super(message);
    }

    public MapperException(String message, Throwable cause) {
        super(message, cause);
    }

    public MapperException(Throwable cause) {
        super(cause);
    }

    public MapperException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
