package com.tractor.framework.db;

import com.tractor.framework.beans.BeansManager;
import com.tractor.framework.config.Config;
import com.tractor.framework.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author 404NotFoundx
 * @date 2019/11/30 2:28
 * @version 1.0.0
 * @since 1.8
 */
public class NativeJdbcImpl implements NativeJdbc {

    private final boolean auto = Config.getTransaction();
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
                if (auto) connection.commit(); // 提交
                return bool;
            } catch (Exception e) {
                if (connection != null && auto) connection.rollback(); // 回滚
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
            NativeResult result = null;
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
                result = BeansManager.newNativeResult(resultSet);
                return result;
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
                if (auto) connection.commit(); // 提交
                return result;
            } catch (Exception e) {
                if (connection != null && auto) connection.rollback(); // 回滚
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
