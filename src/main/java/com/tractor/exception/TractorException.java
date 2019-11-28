package com.tractor.exception;

/**
 * @author 2Bkeyboard
 * @version 1.0.0
 * @date 2019/11/12 11:44
 * @since 1.8
 */
public class TractorException extends Exception{

    public TractorException() {
    }

    public TractorException(String message) {
        super(message);
    }

    public TractorException(String message, Throwable cause) {
        super(message, cause);
    }

    public TractorException(Throwable cause) {
        super(cause);
    }

    public TractorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
