package com.tractor.framework.db;

import com.alibaba.fastjson.JSON;
import com.tractor.framework.cache.NativeCache;
import com.tractor.framework.config.Config;
import com.tractor.framework.pool.ConnectionPool;
import com.tractor.framework.tools.TractorUtils;

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

    private final NativeCache cache = NativeCache.getCache();
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
            String keyMd5 = TractorUtils.encryptToMd5(sql.concat(JSON.toJSONString(args)));
            NativeResult result = cache.get(keyMd5); // 获取缓存
            if (result == null) {
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
                    result = NativeManager.newNativeResult(resultSet);
                    cache.save(keyMd5,result);
                    return result;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (statement != null) statement.close();
                    if (connection != null) pool.release(connection);
                }
            }else{
                return result;
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
            String keyMd5 = TractorUtils.encryptToMd5(sql);
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
                cache.remove(keyMd5);
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
