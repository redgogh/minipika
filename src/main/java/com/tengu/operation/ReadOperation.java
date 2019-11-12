package com.tengu.operation;

/**
 * 读取操作
 *
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/4 14:17
 * @since 1.8
 */
public interface ReadOperation {

    /**
     * 查询操作
     * @param sql
     * @param <T>
     * @return
     */
    <T> T select(String sql);

}
