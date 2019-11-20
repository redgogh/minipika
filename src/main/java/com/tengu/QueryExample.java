package com.tengu;

import com.alibaba.fastjson.JSONObject;
import com.tengu.config.Initialize;
import com.tengu.db.JdbcFunction;
import com.tengu.experiment.UserModel;
import com.tengu.model.IndexModel;
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

        // List<IndexModel> user_model = JdbcFunction.getFunction().getIndexes("user_model");
        // System.out.println(JSONObject.toJSONString(user_model));

        // System.out.println("-------------------------------------------------------------");

        // System.out.println(JSONObject.toJSONString(models));

        System.out.println(JdbcFunction.getFunction().queryForJson("select * from user_model limit 0,10;"));

        long endTime = System.currentTimeMillis();
        System.out.println("耗时：" + (endTime - startTime));
    }

}
