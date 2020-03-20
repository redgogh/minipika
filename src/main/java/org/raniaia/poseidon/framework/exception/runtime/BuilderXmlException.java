package org.raniaia.poseidon.framework.exception.runtime;

/**
 * mapper xml文件异常
 * Copyright: Create by tiansheng on 2019/12/12 18:46
 */
public class BuilderXmlException extends RuntimeException {

    public BuilderXmlException() {
    }

    public BuilderXmlException(String message) {
        super(message);
    }

    public BuilderXmlException(String message, Throwable cause) {
        super(message, cause);
    }

    public BuilderXmlException(Throwable cause) {
        super(cause);
    }

    public BuilderXmlException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
