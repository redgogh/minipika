package com.tengu.db;

import com.tengu.model.TenguResultSet;
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

    private static NativeJdbc njdbc;
    private final ConnectionPool pool = ConnectionPool.getPool();

    public static NativeJdbc getJdbc() {
        if (njdbc == null) njdbc = new NativeJdbc();
        return njdbc;
    }

    @Override
    public boolean execute(String sql, Object... args) {
        try (
                Connection connection = pool.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            return setValues(statement, args).execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public TenguResultSet executeQuery(String sql, Object... args) {
        try (
                Connection connection = pool.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = setValues(statement, args).executeQuery();
        ) {
            return new TenguResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int executeUpdate(String sql, Object... args) {
        try (
                Connection connection = pool.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            return setValues(statement, args).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 设置参数并返回statement
     * @param statement
     * @param args
     * @return
     */
    public PreparedStatement setValues(PreparedStatement statement, Object... args) {
        try {
            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    statement.setObject((i + 1), args[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statement;
    }


}
