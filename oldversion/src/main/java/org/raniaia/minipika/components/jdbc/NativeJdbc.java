package org.jiakesimk.minipika.components.jdbc;

/* ************************************************************************
 *
 * Copyright (C) 2020 tiansheng All rights reserved.
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
 *
 * ************************************************************************/

/*
 * Creates on 2019/11/13.
 */


import org.jiakesimk.minipika.components.jdbc.transaction.TransactionFactory;
import org.jiakesimk.minipika.components.jdbc.transaction.TransactionIsolationLevel;

import java.sql.*;
import java.util.List;

/**
 * NativeJdbc存在的意义是为了方便关闭流和归还连接。
 * 将关闭操作统一控制在NativeJdbc中
 *
 * @author tiansheng
 */
public interface NativeJdbc {

    /*
     * 设置指定事务工厂
     */
    void setTransactionFactory(TransactionFactory transaction);

    /*
     * 设置指定事务公场, 并设置事务的隔离级别
     */
    void setTransactionFactory(TransactionFactory transaction, TransactionIsolationLevel level);

    /*
     * 单独设置事务隔离级别，前提是必须在事务工厂不为NULL的情况下可设置
     */
    void setTransactionIsolationLevel(TransactionIsolationLevel level);

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
     * 批量处理，传入Object[]参数
     * @param sql
     * @param args
     * @return
     */
    int[] executeBatch(String sql, Object... args);

    /**
     * 批量处理，传入List参数
     * @param sql
     * @param args
     * @return
     */
    int[] executeBatch(String sql, List<Object[]> args);

    /**
     * 执行多条sql语句
     * @param sqls
     * @param args
     * @return
     */
    int[] executeBatch(String[] sqls, List<Object[]> args);

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
    default void close(AutoCloseable... closeables) {
        try {
            for (AutoCloseable closeable : closeables) {
                if (closeable != null) {
                    closeable.close();
                }
            }
        } catch (Exception e) {
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

}
