package com.tractor.db;

import com.tractor.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class NativeJdbc implements NativeJdbcService {

    protected final ConnectionPool pool = ConnectionPool.getPool();

    @Override
    public boolean execute(String sql, Object... args) {
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
            try {
                if (statement != null) statement.close();
                if (connection != null) pool.release(connection);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public NativeResultSet executeQuery(String sql, Object... args) {
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
            return new NativeResultSet().build(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) pool.release(connection);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public int executeUpdate(String sql, Object... args) {
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
            try {
                if (statement != null) statement.close();
                if (connection != null) pool.release(connection);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * 设置参数并返回statement
     *
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
