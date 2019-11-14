package com.tengu;

import com.tengu.db.NativeJdbc;
import com.tengu.model.TenguResultSet;
import com.tengu.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/4 14:22
 * @since 1.8
 */
public class Example {

    static ConnectionPool pool = ConnectionPool.getPool();

    public static void main(String[] args) throws Throwable {

        String sql = "select * from test";

        NativeJdbc jdbc = NativeJdbc.getJdbc();

        TenguResultSet rset = jdbc.executeQuery(sql);

        System.out.println();

    }

    public static void execute(Connection connection, String sql) throws Exception {
        PreparedStatement ps = null;
        ps = connection.prepareStatement(sql);

        ResultSet rset = ps.executeQuery();
        rset.next();

        System.out.println(rset.getString(1));

        System.out.println();
    }

}
