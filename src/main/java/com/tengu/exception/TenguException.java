package com.tengu.exception;

/**
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/12 11:44
 * @since 1.8
 */
public class TenguException extends Exception{

    public TenguException() {
    }

    public TenguException(String message) {
        super(message);
    }

    public TenguException(String message, Throwable cause) {
        super(message, cause);
    }

    public TenguException(Throwable cause) {
        super(cause);
    }

    public TenguException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
