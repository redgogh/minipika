package com.tengu;

import com.tengu.config.Initialize;
import com.tengu.db.JdbcFunction;
import com.tengu.db.NativeJdbc;
import com.tengu.experiment.UserModel;
import com.tengu.model.IndexModel;
import com.tengu.model.ModelAttribute;
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

        // JdbcFunction.getFunction().getEngine("user_model");
        // UserModel models = JdbcFunction.getFunction().queryForObject("select * from user_model where id = 1", UserModel.class);

        long endTime = System.currentTimeMillis();
        System.out.println("耗时：" + (endTime - startTime));
    }

}
