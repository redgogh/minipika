package org.laniakeamly.poseidon.framework.db;

import org.laniakeamly.poseidon.extension.ConnectionPool;

import java.sql.*;
import java.util.List;

/**
 * NativeJdbc存在的意义是为了方便关闭流和归还连接。
 * 将关闭操作统一控制在NativeJdbc中
 *
 * @author 2BKeyboard
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
     * 批量处理
     * @param sql
     * @param args
     * @return
     */
    int[] executeBatch(String sql, Object... args);

    int[] executeBatch(String sql, List<Object[]> args);

    // 添加预编译sql的参数
    default PreparedStatement setValues(PreparedStatement statement, Object... args) throws SQLException {
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                statement.setObject((i + 1), args[i]);
            }
        }
        return statement;
    }

    // 关闭流
    default void close(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 回滚
    default void rollback(Connection connection, boolean auto) {
        try {
            if (connection != null && auto) connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 归还连接
    default void release(Connection connection, ConnectionPool pool) {
        if (connection != null) pool.release(connection);
    }

}
