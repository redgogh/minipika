package com.tractor.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * NativeJdbc存在的意义是为了方便关闭流和归还连接。
 * 将关闭操作统一控制在NativeJdbc中
 *
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/13 16:57
 * @since 1.8
 */
public interface NativeJdbc {

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
    NativeResult executeQuery(String sql, Object... args);

    /**
     * 执行更新
     *
     * @param sql
     * @param args
     * @return
     */
    int executeUpdate(String sql, Object... args);

    /**
     * 设置 statement 参数
     *
     * @param statement
     * @param args
     * @return
     */
    default PreparedStatement setValues(PreparedStatement statement, Object... args) throws SQLException {
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                statement.setObject((i + 1), args[i]);
            }
        }
        return statement;
    }

}
