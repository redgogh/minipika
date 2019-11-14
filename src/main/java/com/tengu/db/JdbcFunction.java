package com.tengu.db;

import com.tengu.config.Config;
import com.tengu.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/11 23:40
 * @since 1.8
 */
public class JdbcFunction implements JdbcFunctionService {

    private static JdbcFunction template;
    private static NativeJdbc nativeJdbc = NativeJdbc.getJdbc();

    private ConnectionPool pool = ConnectionPool.getPool();

    public static JdbcFunction getFunction() {
        if (template == null) {
            template = new JdbcFunction();
        }
        return template;
    }

    @Override
    public <T> T queryForObject(String sql, Class<T> obj, Object... args) {
        return nativeJdbc.executeQuery(sql,args).conversionJavaBean(obj);
    }

    @Override
    public <T> List<T> queryForList(String sql, Class<T> obj, Object... args) {
        return nativeJdbc.executeQuery(sql,args).conversionJavaList(obj);
    }

    @Override
    public Long update(Object obj) {
        return null;
    }

    @Override
    public Long update(String sql, Object... args) {
        return null;
    }

    @Override
    public Long updateDoNULL(Object obj) {
        return null;
    }

    @Override
    public Long insert(String sql, Object... args) {
        return null;
    }

    @Override
    public <T> Long insert(T model) {
        return null;
    }

    @Override
    public Long delete(String sql, Object... args) {
        return null;
    }

    @Override
    public Long delete(String id) {
        return null;
    }

    @Override
    public ArrayList<String> getColumns(String tableName) {
        ArrayList<String> columns = new ArrayList<>();
        String sql = "select COLUMN_NAME " +
                "from information_schema.COLUMNS " +
                "where table_name = '" + tableName + "' " +
                "and table_schema = '" + Config.getDbname() + "';";
        try {
            Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rset = statement.executeQuery();
            while (rset.next()) {
                columns.add(rset.getString(1));
            }
            System.out.println();
            connection.close();
            statement.close();
            rset.close();
        } catch (SQLException e) {
            System.out.println("sql执行异常，执行SQL如下:");
            System.out.println(sql);
            e.printStackTrace();
        }
        return columns;
    }

    @Override
    public boolean execute(String sql) {
        boolean r = false;
        try {
            Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            r = statement.execute();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("sql执行异常，执行SQL如下:");
            System.out.println(sql);
            e.printStackTrace();
        }
        return r;
    }

}
