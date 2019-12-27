package org.laniakeamly.poseidon.extension;

/**
 * 使用自定义的日志框架，如果没有使用默认。
 *
 * Configuration logger framework if there is not use default.
 *
 * Create by 2BKeyboard on 2019/12/9 17:20
 */
public interface Logger {

    void info(String v);

    void warn(String v);

    void debug(String v);

    void error(String v);

}
