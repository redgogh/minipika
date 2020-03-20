package org.raniaia.poseidon.components.log;

/**
 * 使用自定义的日志框架，如果没有使用默认。
 *
 * Configuration logger framework if there is not use default.
 *
 * Copyright: Create by tiansheng on 2019/12/9 17:20
 */
public interface Log {

    void info(String msg);

    void warn(String msg);

    void debug(String msg);

    void error(String msg);

}
