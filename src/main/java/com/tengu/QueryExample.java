package com.tengu;

import com.alibaba.fastjson.JSONObject;
import com.tengu.db.JdbcFunction;
import com.tengu.experiment.UserModel;
import com.tengu.pool.ConnectionPool;

import java.util.Date;
import java.util.UUID;

/**
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/4 14:22
 * @since 1.8
 */
public class QueryExample {

    public static void main(String[] args) throws Throwable {

        long startTime = System.currentTimeMillis();

        // JdbcFunction.getFunction().getEngine("user_model");
        // UserModel models = JdbcFunction.getFunction().queryForObject("select * from user_model where id = ?", UserModel.class,397463);

        // List<IndexModel> user_model = JdbcFunction.getFunction().getIndexes("user_model");
        // System.out.println(JSONObject.toJSONString(user_model));

        // System.out.println("-------------------------------------------------------------");

        // System.out.println(JSONObject.toJSONString(models));

        // System.out.println(JdbcFunction.getFunction().queryForJson("select * from user_model limit 0,10;"));

        /*for(int j=0; j<100; j++){
            UserModel model = new UserModel();
            String uuid = UUID.randomUUID().toString();
            model.setUserName(uuid);
            model.setUuid(uuid);
            model.setCreateTime(new Date());
            JdbcFunction.getFunction().insert(model);
        }*/

        System.out.println(JSONObject.toJSONString(JdbcFunction.getFunction().getColumns("user_model")));

        // System.out.println(Config.getDriver());

        long endTime = System.currentTimeMillis();
        System.out.println("耗时：" + (endTime - startTime));
    }

}
