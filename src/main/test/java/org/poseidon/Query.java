package org.poseidon;

/*
 * Copyright (C) 2020 Tiansheng All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * Creates on TODO DATE
 */


import org.poseidon.experiment.ProductModel;
import org.poseidon.experiment.UserModel;
import org.raniaia.poseidon.BeansManager;
import org.raniaia.poseidon.components.cache.PoseidonCache;
import org.raniaia.poseidon.components.db.JdbcSupport;
import org.raniaia.poseidon.framework.tools.POFUtils;
import org.raniaia.poseidon.framework.tools.DateUtils;

import java.util.Date;

/**
 * @author tiansheng
 * @version 1.0.0
 * @date 2019/11/4 14:22
 * @since 1.8
 */
public class Query {

    static JdbcSupport jdbc     = BeansManager.get("jdbc");
    static PoseidonCache cache  = BeansManager.get("cache");

    public static void main(String[] args) throws Throwable {

        long startTime = System.currentTimeMillis();

        // List<UserModel> models = JdbcSupport.getTemplate().queryForList("select * from user_model", UserModel.class);

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

        // WalletModel walletModel = jdbc.queryForObject("select * from kkb_wallet where id = 1",WalletModel.class);

        cacheTest();

    }

    public static void cacheTest() throws Exception{

        while (true) {
            System.out.println("[执行查询]");
            String sql = "select * from kkb_user_model as u left join kkb_product_model as p on u.uuid = p.uuid where u.id = ?";

            String uuid = POFUtils.uuid();
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

            ProductModel queryModel = jdbc.queryForObject("select * from kkb_product_model where id = ?", ProductModel.class, 6);
            jdbc.update(queryModel);

            jdbc.queryForJson(sql, 1002001);

            Thread.sleep(DateUtils.MINUTE);
            // long endTime = System.currentTimeMillis();
            // System.out.println("查询【" + models.size() + "】条数据，耗时：" + (endTime - startTime));
        }

    }

}
