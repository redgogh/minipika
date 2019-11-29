package com.tractor.framework.db;

import com.tractor.framework.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 手动提交（包含事物）
 * Create by 2BKeyboard on 2019/11/28 17:30
 */
public class NativeJdbcManualCommit implements NativeJdbc {

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
                Boolean bool = setValues(statement, args).execute();
                connection.commit(); // 提交
                return bool;
            } catch (Exception e) {
                if (connection != null) connection.rollback(); // 回滚
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
                int result = setValues(statement, args).executeUpdate();
                connection.commit(); // 提交
                return result;
            } catch (Exception e) {
                if (connection != null) connection.rollback(); // 回滚
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
