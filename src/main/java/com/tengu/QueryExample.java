package com.tengu;

import com.tengu.db.JdbcFunction;
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

        List<IndexModel> models = JdbcFunction.getFunction().getIndexes("user_model");

        long endTime = System.currentTimeMillis();
        System.out.println("耗时：" + (endTime - startTime));
    }

}
