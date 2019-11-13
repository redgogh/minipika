package com.tengu.db;

import com.tengu.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/13 16:56
 * @since 1.8
 */
public class NativeJdbc implements NativeJdbcService {

    private final ConnectionPool pool = ConnectionPool.getPool();

    @Override
    public ResultSet executeQuery(String sql, Object... args) {
        try (
                Connection connection = pool.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();
        ) {

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
