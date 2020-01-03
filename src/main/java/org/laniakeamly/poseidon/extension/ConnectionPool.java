package org.laniakeamly.poseidon.extension;

import java.sql.Connection;

/**
 * 这个接口被实现后可以替换默认的连接池。 {@link org.laniakeamly.poseidon.framework.pool.ConnectionPool}
 *
 * 如果你不想使用默认自带的连接池，可以选择使用其他连接池实现该接口。
 *
 * this interface can implement.
 * implemented and config can replace default connection pool {@link org.laniakeamly.poseidon.framework.pool.ConnectionPool}
 *
 * if you don't want use default connection pool.
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
