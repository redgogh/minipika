package com.tractor.db;

/**
 * NativeJdbc存在的意义是为了方便关闭流和归还连接。
 * 将关闭操作统一控制在NativeJdbc中
 *
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/13 16:57
 * @since 1.8
 */
public interface NativeJdbcService {

    /**
     * 执行任何sql语句
     *
     * @param sql
     * @param args
     * @return
     */
    boolean execute(String sql, Object... args);

    /**
     * 执行查询
     *
     * @param sql
     * @param args
     * @return
     */
    NativeResultSet executeQuery(String sql, Object... args);

    /**
     * 执行更新
     *
     * @param sql
     * @param args
     * @return
     */
    int executeUpdate(String sql, Object... args);

}
