package com.tengu;

import com.tengu.annotation.Model;
import com.tengu.db.JdbcFunction;
import com.tengu.experiment.UserModel;
import com.tengu.pool.ConnectionPool;
import java.util.List;

/**
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/4 14:22
 * @since 1.8
 */
public class QueryExample {

    static ConnectionPool pool = ConnectionPool.getPool();

    public static void main(String[] args) throws Throwable {

        long startTime = System.currentTimeMillis();

        String sql = "select * from user_model where user_name = '019cda9b-1da7-4d0d-9a9d-04ff7a2d8aea'";

        // List<UserModel> models = JdbcFunction.getFunction().queryForList(sql,UserModel.class);
        UserModel model = JdbcFunction.getFunction().queryForObject(sql,UserModel.class);

        model.setPassword("abcccc12333");
        model.setAddress(null);

        int row = JdbcFunction.getFunction().update(model);
        System.out.println(row);

        long endTime = System.currentTimeMillis();
        // System.out.println("一共查询到了 "+models.size()+" 条数据，耗时："+(endTime - startTime));
    }

}
