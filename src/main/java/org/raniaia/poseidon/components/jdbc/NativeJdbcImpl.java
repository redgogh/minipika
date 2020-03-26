package org.raniaia.poseidon.components.jdbc;

/*
 * Copyright (C) 2020 Tiansheng All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * Creates on 2019/11/30.
 */

import org.raniaia.poseidon.BeansManager;
import org.raniaia.poseidon.components.jdbc.datasource.pooled.PooledConnection;
import org.raniaia.poseidon.components.jdbc.transaction.Transaction;
import org.raniaia.poseidon.components.jdbc.transaction.TransactionFactory;
import org.raniaia.poseidon.components.jdbc.transaction.TransactionIsolationLevel;
import org.raniaia.poseidon.components.jdbc.transaction.jdbc.JdbcTransaction;
import org.raniaia.poseidon.components.log.LogFactory;

import org.raniaia.poseidon.components.log.Log;
import org.raniaia.poseidon.framework.provide.Valid;
import org.raniaia.poseidon.components.cache.PoseidonCache;
import org.raniaia.poseidon.components.config.GlobalConfig;
import org.raniaia.poseidon.framework.provide.component.Component;
import org.raniaia.poseidon.framework.tools.Arrays;
import org.raniaia.poseidon.framework.tools.SQLUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Native jdbc operation.
 *
 * @author tiansheng
 */
@Component
@SuppressWarnings("SpellCheckingInspection")
public class NativeJdbcImpl implements NativeJdbc {


    @Valid
    private PoseidonCache cache;

    @Valid
    private DataSource dataSource;

    protected TransactionFactory transactionFactory;

    private Log log                                     =       LogFactory.getLog(NativeJdbcImpl.class);

    protected final boolean isCache                     =       GlobalConfig.getConfig().getCache();
    protected final boolean desiredAutoCommit           =       GlobalConfig.getConfig().getdesiredAutoCommit();

    public NativeJdbcImpl(){}

    public NativeJdbcImpl(TransactionFactory transactionFactory){
        this.transactionFactory = transactionFactory;
    }

    @Override
    public void setTransactionFactory(TransactionFactory transactionFactory) {
        this.transactionFactory = transactionFactory;
    }

    @Override
    public void setTransactionFactory(TransactionFactory transaction, TransactionIsolationLevel level) {

    }

    @Override
    public void setTransactionIsolationLevel(TransactionIsolationLevel level) {

    }

    @Override
    public boolean execute(String sql, Object... args) {
        if(log.isDebugEnabled()) {
            log.debug("execute: " + sql);
        }
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            Boolean bool = setValues(statement, args).execute();
            if (desiredAutoCommit) connection.commit(); // 提交
            return bool;
        } catch (Exception e) {
            rollback(connection, desiredAutoCommit);
            e.printStackTrace();
        } finally {
            close(connection, statement, null);
        }
        return false;
    }

    @Override
    public NativeResult executeQuery(String sql, Object... args) {
        if(log.isDebugEnabled()){
            log.debug("query: " + sql);
        }
        NativeResult result = null;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            // 判断是否开启缓存
            if (isCache) {
                result = cache.get(sql, args);
                if (result == null) {
                    ResultSet resultSet = setValues(statement, args).executeQuery();
                    result = BeansManager.newNativeResult().build(resultSet);
                    cache.save(sql, result, args);
                    return cache.get(sql, args);
                }
                return result;
            } else {
                ResultSet resultSet = setValues(statement, args).executeQuery();
                return BeansManager.newNativeResult().build(resultSet);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            close(connection, statement, null);
        }
        return null;
    }

    @Override
    public int executeUpdate(String sql, Object... args) {
        if(log.isDebugEnabled()){
            log.debug("update: " + sql);
        }
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            int result = setValues(statement, args).executeUpdate();
            if (desiredAutoCommit) connection.commit(); // 提交
            if (isCache) cache.refresh(sql); // 刷新缓存
            return result;
        } catch (Throwable e) {
            rollback(connection, desiredAutoCommit); // 回滚
            e.printStackTrace();
        } finally {
            close(connection, statement, null);
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
            connection = dataSource.getConnection();
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
            if (desiredAutoCommit) connection.commit();
            if (isCache) cache.refresh(sql);
            return result;
        } catch (Throwable e) {
            rollback(connection, desiredAutoCommit); // 回滚
            e.printStackTrace();
        } finally {
            close(connection, statement, null);
        }
        return new int[0];
    }

    @Override
    public int[] executeBatch(String[] sqls, List<Object[]> args) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            int index = -1;
            for (String sql : sqls) {
                statement.addBatch(SQLUtils.buildPreSQL(sql, args.get(index = (index + 1))));
            }
            int[] result = statement.executeBatch();
            if (desiredAutoCommit) connection.commit();
            if (isCache) {
                for (String sql : sqls) cache.refresh(sql);
            }
            return result;
        } catch (Throwable e) {
            rollback(connection, desiredAutoCommit);
            e.printStackTrace();
        } finally {
            close(connection, statement, null);
        }
        return new int[0];
    }
}
