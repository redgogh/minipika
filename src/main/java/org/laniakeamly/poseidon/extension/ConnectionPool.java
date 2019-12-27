package org.laniakeamly.poseidon.extension;

import java.sql.Connection;

/**
 * JDBC连接池，如果没有配置使用自带的连接池。
 *
 * JDBC Connection Pool if not config use default connection pool.
 *
 * Create by 2BKeyboard on 2019/12/9 17:19
 */
public interface ConnectionPool {

    /**
     * 创建链接
     * @return
     */
    Connection createConnection();

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
