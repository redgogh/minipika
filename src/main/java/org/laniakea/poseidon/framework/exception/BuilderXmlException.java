package org.laniakea.poseidon.framework.exception;

/**
 * mapper xml文件异常
 * Create by 2BKeyboard on 2019/12/12 18:46
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
