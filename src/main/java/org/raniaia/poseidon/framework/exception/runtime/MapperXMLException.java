package org.raniaia.poseidon.framework.exception.runtime;

/**
 * Copyright: Create by TianSheng on 2019/12/18 14:56
 */
public class MapperXMLException extends RuntimeException {

    public MapperXMLException() {
    }

    public MapperXMLException(String message) {
        super(message);
    }

    public MapperXMLException(String message, Throwable cause) {
        super(message, cause);
    }

    public MapperXMLException(Throwable cause) {
        super(cause);
    }

    public MapperXMLException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
