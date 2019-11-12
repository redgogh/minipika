package com.tengu.exception;

/**
 * 解析异常
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/12 11:51
 * @since 1.8
 */
public class ParseException extends TenguException {

    public ParseException() {
    }

    public ParseException(String message) {
        super(message);
    }
}
