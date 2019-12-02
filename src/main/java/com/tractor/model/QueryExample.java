package com.tractor.model;

import com.tractor.framework.db.JdbcSupport;

/**
 * @author 2BKeyboard
 * @version 1.0.0
 * @date 2019/11/4 14:22
 * @since 1.8
 */
public class QueryExample {

    public static void main(String[] args) throws Throwable {

        long startTime = System.currentTimeMillis();

        // List<UserModel> models = JdbcSupport.getTemplate().queryForList("select * from user_model", UserModel.class);

        JdbcSupport jdbc = JdbcSupport.getTemplate();
        // UserModel models = JdbcFunction.getTemplate().queryForObject("select * from user_model where id = ?", UserModel.class,397463);

        // List<IndexModel> user_model = JdbcFunction.getTemplate().getIndexes("user_model");
        // System.out.println(JSONObject.toJSONString(user_model));

        // System.out.println("-------------------------------------------------------------");

        // System.out.println(JSONObject.toJSONString(models));

        // System.out.println(JdbcFunction.getTemplate().queryForJson("select * from user_model limit 0,10;"));

        /*for(int j=0; j<100; j++){
            UserModel model = new UserModel();
            String uuid = UUID.randomUUID().toString();
            model.setUserName(uuid);
            model.setUuid(uuid);
            model.setCreateTime(new Date());
            JdbcFunction.getTemplate().insert(model);
        }*/

        // System.out.println(JSONObject.toJSONString(jdbc.getColumns("user_model")));

        // System.out.println(Config.getDriver());

        // new UnsafeJdbc().clear(UserModel.class);

        jdbc.queryForJson("select * from user_model limit 0,10");
        jdbc.queryForJson("select * from user_model limit 0,10");
        jdbc.queryForJson("select * from user_model as u left join product_model as p on u.product_name = p.product_name");

        // long endTime = System.currentTimeMillis();
        // System.out.println("查询【" + models.size() + "】条数据，耗时：" + (endTime - startTime));
    }

}
