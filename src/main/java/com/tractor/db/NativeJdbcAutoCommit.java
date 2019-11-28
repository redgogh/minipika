package com.tractor.db;

import com.tractor.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 自动提交（没有事物）
 *
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/13 16:57
 * @since 1.8
 */
public class NativeJdbcAutoCommit implements NativeJdbc {

    protected final ConnectionPool pool = ConnectionPool.getPool();

    @Override
    public boolean execute(String sql, Object... args) {
        try {
            Connection connection = null;
            PreparedStatement statement = null;
            try {
                connection = pool.getConnection();
                if (connection == null) {
                    synchronized (this) {
                        wait();
                    }
                    return execute(sql, args);
                }
                statement = connection.prepareStatement(sql);
                return setValues(statement, args).execute();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (statement != null) statement.close();
                if (connection != null) pool.release(connection);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public NativeResult executeQuery(String sql, Object... args) {
        try {
            Connection connection = null;
            PreparedStatement statement = null;
            try {
                connection = pool.getConnection();
                if (connection == null) {
                    synchronized (this) {
                        wait();
                    }
                    return executeQuery(sql, args);
                }
                statement = connection.prepareStatement(sql);
                ResultSet resultSet = setValues(statement, args).executeQuery();
                return new NativeResult().build(resultSet);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (statement != null) statement.close();
                if (connection != null) pool.release(connection);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int executeUpdate(String sql, Object... args) {
        try {
            Connection connection = null;
            PreparedStatement statement = null;
            try {
                connection = pool.getConnection();
                if (connection == null) {
                    synchronized (this) {
                        wait();
                    }
                    executeUpdate(sql, args);
                }
                statement = connection.prepareStatement(sql);
                int r = setValues(statement, args).executeUpdate();
                return r;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (statement != null) statement.close();
                if (connection != null) pool.release(connection);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return 0;
    }

}
