package org.raniaia.poseidon.framework.db;

import org.raniaia.poseidon.extension.ConnectionPool;
import org.raniaia.poseidon.modules.log.Log;
import org.raniaia.poseidon.framework.provide.Valid;
import org.raniaia.poseidon.framework.context.PoseBeansManager;
import org.raniaia.poseidon.framework.cache.PoseidonCache;
import org.raniaia.poseidon.framework.config.GlobalConfig;
import org.raniaia.poseidon.framework.tools.Arrays;
import org.raniaia.poseidon.framework.tools.SQLUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 底层JDBC处理
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/30 2:28
 * @since 1.8
 */
@SuppressWarnings("SpellCheckingInspection")
public class NativeJdbcImpl implements NativeJdbc {

    protected final boolean isCache = GlobalConfig.getConfig().getCache();
    protected final boolean transaction = GlobalConfig.getConfig().getTransaction();

    @Valid
    private ConnectionPool pool;

    @Valid
    private PoseidonCache cache;

    @Valid(name = "logger")
    private Log logger;

    @Override
    public boolean execute(String sql, Object... args) {
        logger.debug("execute: " + sql);
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = pool.getConnection();
            /*if (connection == null) {
                synchronized (this) {
                    wait();
                }
                return execute(sql, args);
            }*/
            statement = connection.prepareStatement(sql);
            Boolean bool = setValues(statement, args).execute();
            if (transaction) connection.commit(); // 提交
            return bool;
        } catch (Exception e) {
            rollback(connection, transaction);
            e.printStackTrace();
        } finally {
            close(null, statement, null);
            release(connection, pool);
        }
        return false;
    }

    @Override
    public NativeResult executeQuery(String sql, Object... args) {
        logger.debug("query: " + sql);
        NativeResult result = null;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = pool.getConnection();
            /*if (connection == null) {
                synchronized (this) {
                    wait();
                }
                return executeQuery(sql, args);
            }*/
            statement = connection.prepareStatement(sql);
            // 判断是否开启缓存
            if (isCache) {
                result = cache.get(sql, args);
                if (result == null) {
                    ResultSet resultSet = setValues(statement, args).executeQuery();
                    result = PoseBeansManager.newNativeResult().build(resultSet);
                    cache.save(sql, result, args);
                    return cache.get(sql, args);
                }
                return result;
            } else {
                ResultSet resultSet = setValues(statement, args).executeQuery();
                return PoseBeansManager.newNativeResult().build(resultSet);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            close(null, statement, null);
            release(connection, pool);
        }
        return null;
    }

    @Override
    public int executeUpdate(String sql, Object... args) {
        logger.debug("update: " + sql);
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = pool.getConnection();
            /*if (connection == null) {
                synchronized (this) {
                    wait();
                }
                executeUpdate(sql, args);
            }*/
            statement = connection.prepareStatement(sql);
            int result = setValues(statement, args).executeUpdate();
            if (transaction) connection.commit(); // 提交
            if (isCache) cache.refresh(sql); // 刷新缓存
            return result;
        } catch (Throwable e) {
            rollback(connection, transaction); // 回滚
            e.printStackTrace();
        } finally {
            close(null, statement, null);
            release(connection, pool);
        }
        return 0;
    }

    @Override
    public int[] executeBatch(String sql, List<Object[]> args) {
        return this.executeBatch(sql, args.toArray());
    }

    @Override
    public int[] executeBatch(String sql, Object... args) {
        // 判断sql中是否包含多条sql，根据';'来判断
        out:
        if (sql.contains(";")) {
            String[] sqls = (String[]) Arrays.remove(sql.split(";"), Arrays.Op.LAST);
            // 如果sql包含';'，但是数组中只有一条sql的话就跳出if
            if (sqls.length == 1) break out;
            List<Object[]> objList = new ArrayList<>();
            int argsIndex = 0;
            for (String isql : sqls) {
                int length = 0;
                for (char chara : isql.toCharArray()) {
                    if (chara == '?') length++;
                }
                Object[] objects = new Object[length];
                System.arraycopy(args, argsIndex, objects, 0, length);
                argsIndex = length;
                objList.add(objects);
            }
            return executeBatch(sqls, objList);
        }
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement(sql);
            for (Object arg : args) {
                Object[] value = (Object[]) arg;
                int i = 1;
                for (Object o : value) {
                    statement.setObject(i, o);
                    i++;
                }
                statement.addBatch();
            }
            int[] result = statement.executeBatch();
            if (transaction) connection.commit();
            if (isCache) cache.refresh(sql);
            return result;
        } catch (Throwable e) {
            rollback(connection, transaction); // 回滚
            e.printStackTrace();
        } finally {
            close(null, statement, null);
            release(connection, pool);
        }
        return new int[0];
    }

    @Override
    public int[] executeBatch(String[] sqls, List<Object[]> args) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = pool.getConnection();
            statement = connection.createStatement();
            int index = -1;
            for (String sql : sqls) {
                statement.addBatch(SQLUtils.buildPreSQL(sql, args.get(index = (index + 1))));
            }
            int[] result = statement.executeBatch();
            if (transaction) connection.commit();
            if (isCache) {
                for (String sql : sqls) cache.refresh(sql);
            }
            return result;
        } catch (Throwable e) {
            rollback(connection, transaction);
            e.printStackTrace();
        } finally {
            close(null, statement, null);
            release(connection, pool);
        }
        return new int[0];
    }
}
