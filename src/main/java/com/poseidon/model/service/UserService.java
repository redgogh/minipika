package com.poseidon.model.service;

import com.poseidon.framework.beans.BeansManager;
import com.poseidon.framework.db.JdbcSupport;
import com.poseidon.framework.sql.SqlBuilder;
import com.poseidon.model.experiment.UserModel;
import com.poseidon.framework.tools.PoseidonUtils;

import java.util.Date;

/**
 * @author 2BKeyboard
 * @version 1.0.0
 * @date 2019/11/27 17:33
 * @since 1.8
 */
public class UserService {

    private JdbcSupport jdbc = BeansManager.getBean("jdbc");

    public UserModel findUserById(String id) {

        /*

            SELECT
            	a.*,
            	b.contract_name AS contract_order_currencyname,
            	b.contract_currencytype AS contract_order_currencytype,
            	c.contract_pl_closeprice AS contract_order_closeprice,
            	c.contract_pl_profit AS contract_order_profit
            FROM
            	shiqi_contract_order a
            	LEFT JOIN shiqi_contract b ON b.contract_key = a.contract_order_currencykey
            	LEFT JOIN shiqi_contract_pl c ON c.contract_pl_ordernumber = a.contract_order_id
            WHERE
            	a.contract_order_account = ?
            	AND a.contract_order_status > 1
            ORDER BY
            	a.contract_order_createtime DESC
            	LIMIT ?,?;

         */

        SqlBuilder sql = new SqlBuilder();

        sql.select();
        sql.addColumn("a.*");
        sql.addColumn("b.contract_name","contract_order_currencyname");
        sql.addColumn("b.contract_currencytype","contract_order_currencytype");
        sql.addColumn("b.contract_pl_closeprice","contract_order_closeprice");
        sql.addColumn("b.contract_pl_profit","contract_order_profit");
        sql.selectEnd();

        sql.from();
        sql.addScript("shiqi_contract_order a");
        sql.addScript("LEFT JOIN shiqi_contract b ON b.contract_key = a.contract_order_currencykey");
        sql.addScript("LEFT JOIN shiqi_contract_pl c ON c.contract_pl_ordernumber = a.contract_order_id");

        return jdbc.queryForObject(sql.toString(), UserModel.class, sql.toArray());
    }

}
