package com.poseidon.customize;

import java.sql.Connection;

/**
 * Create by 2BKeyboard on 2019/12/9 17:19
 */
public interface ConnectionPool {

    /**
     * 获取连接
     * @return
     */
    Connection getConnection();

    /**
     * 归还连接
     * @param connection
     */
    void release(Connection connection);

}
