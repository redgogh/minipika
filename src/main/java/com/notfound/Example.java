package com.notfound;

import com.notfound.annotation.BaseTable;
import com.notfound.annotation.Table;
import com.notfound.config.Config;
import com.notfound.db.ConnectionPool;

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

    private static ConnectionPool pool = ConnectionPool.getConnectionPool();

    public static void main(String[] args) throws Exception{

        String sql = "select * from test";
        Connection connection = pool.getQueryConnection();

        PreparedStatement ps = null;
        ps = connection.prepareStatement(sql);

        ResultSet rset = ps.executeQuery();
        rset.next();

        System.out.println(rset.getString(1));

        pool.releaseQuery(connection);

    }

}
