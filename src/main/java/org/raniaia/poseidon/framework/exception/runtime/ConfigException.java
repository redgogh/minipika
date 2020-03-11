package org.raniaia.poseidon.framework.exception.runtime;

/**
 * Copyright by TianSheng on 2020/2/13 0:18
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 */
public class ConfigException extends RuntimeException {

    public ConfigException() {
    }

    public ConfigException(String message) {
        super(message);
    }

    public ConfigException(String message, StringBuilder logger) {
        super(message + "\n" + logger);
    }

    public ConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigException(Throwable cause) {
        super(cause);
    }

    public ConfigException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
