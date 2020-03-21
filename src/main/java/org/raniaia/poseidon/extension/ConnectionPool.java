package org.raniaia.poseidon.extension;

import java.sql.Connection;

/**
 * 这个接口被实现后可以替换默认的连接池。 {@link org.raniaia.poseidon.components.pool.ConnectionPool}
 *
 * 如果你不想使用默认自带的连接池，可以选择使用其他连接池实现该接口。
 *
 * this interface can implement.
 * implemented and config can replace default connection pool {@link org.raniaia.poseidon.components.pool.ConnectionPool}
 *
 * if you don't want use default connection pool.
 *
 * Copyright: Create by tiansheng on 2019/12/9 17:19
 */
public interface ConnectionPool {

    /**
     * 获取连接
     */
    Connection getConnection();

    /**
     * 归还连接
     */
    void release(Connection connection);

}
