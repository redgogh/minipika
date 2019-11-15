package com.tengu;

import com.tengu.db.JdbcFunction;
import com.tengu.experiment.UserModel;
import com.tengu.pool.ConnectionPool;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/4 14:22
 * @since 1.8
 */
public class Example {

    static ConnectionPool pool = ConnectionPool.getPool();

    public static void main(String[] args) throws Throwable {

        String listSQL = "select * from user_model";
        String singleSQL = "select * from user_model where id = ?";

        List<UserModel> models = JdbcFunction.getFunction().queryForList(listSQL,UserModel.class);
        UserModel model = JdbcFunction.getFunction().queryForObject(singleSQL,UserModel.class,1);

        System.out.println(model.toString());

        System.out.println("\n=============================================\n");

        for(UserModel m : models){
            System.out.println(m.toString());
        }

        UserModel userModel = new UserModel();
        userModel.setUserName("刘备");
        userModel.setPassword("liubei123");
        userModel.setGoogleEmail("liubei@gmail");
        userModel.setUserAge(60);

        int sum = JdbcFunction.getFunction().update(userModel);
        System.out.println("update: " + sum);

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
