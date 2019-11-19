package com.tengu;

import com.tengu.db.JdbcFunction;
import com.tengu.experiment.UserModel;
import com.tengu.model.ModelAttribute;
import com.tengu.pool.ConnectionPool;

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

        String sql = "select * from user_model where id = 1";
        String showIndexSQL = "show index from user_model";
        // List<UserModel> models = JdbcFunction.getFunction().queryForList(sql,UserModel.class);
        UserModel model = JdbcFunction.getFunction().queryForObject(sql,UserModel.class);

        model.setPassword("abcccc12333");
        model.setAddress(null);

        int row = JdbcFunction.getFunction().update(model);
        ModelAttribute.getModelClass("user_model");
        System.out.println(row);

        long endTime = System.currentTimeMillis();
        // System.out.println("一共查询到了 "+models.size()+" 条数据，耗时："+(endTime - startTime));
    }

}
