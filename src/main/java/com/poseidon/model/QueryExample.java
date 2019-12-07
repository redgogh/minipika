package com.poseidon.model;

import com.poseidon.framework.beans.BeansManager;
import com.poseidon.framework.cache.PoseidonCache;
import com.poseidon.framework.config.ManualConfig;
import com.poseidon.framework.db.JdbcSupport;
import com.poseidon.framework.db.PageHelper;
import com.poseidon.framework.tools.PoseidonUtils;
import com.poseidon.framework.tools.StringUtils;
import com.poseidon.framework.tools.TimeUtils;
import com.poseidon.model.experiment.ProductModel;
import com.poseidon.model.experiment.UserModel;

import java.util.Date;

/**
 * @author 2BKeyboard
 * @version 1.0.0
 * @date 2019/11/4 14:22
 * @since 1.8
 */
public class QueryExample {

    static PoseidonCache cache = BeansManager.getPoseidonCache();

    public static void main(String[] args) throws Throwable {

        long startTime = System.currentTimeMillis();

        // List<UserModel> models = JdbcSupport.getTemplate().queryForList("select * from user_model", UserModel.class);

        ManualConfig.load("poseidon.properties");
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

        // System.out.println(jdbc.count("select * from user_model limit 0,10"));
        // jdbc.queryForJson("select * from user_model as u left join product_model as p on u.product_name = p.product_name");

        while(true) {

            System.out.println("[执行查询]");
            String sql = "select * from kkb_user_model as u left join kkb_product_model as p on u.uuid = p.uuid where u.id = ?";

            String uuid = PoseidonUtils.uuid();
            UserModel userModel = new UserModel();
            userModel.setUserName(uuid);
            userModel.setGoogleEmail(uuid);
            userModel.setAddress(uuid);
            userModel.setUuid(uuid);
            userModel.setCreateTime(new Date());

            ProductModel productModel = new ProductModel();
            productModel.setProductName("产品[".concat(String.valueOf(new Date().getTime())).concat("]"));
            productModel.setUuid(uuid);

            jdbc.insert(userModel);
            jdbc.insert(productModel);

            jdbc.queryForJson(sql, 1002001);
            jdbc.queryForJson(sql, 1002001);

            ProductModel queryModel = jdbc.queryForObject("select * from kkb_product_model where id = ?", ProductModel.class, 33);
            jdbc.update(queryModel);

            jdbc.queryForJson(sql, 1002001);

            Thread.sleep(TimeUtils.MINUTE);
            // long endTime = System.currentTimeMillis();
            // System.out.println("查询【" + models.size() + "】条数据，耗时：" + (endTime - startTime));
        }
    }

}
