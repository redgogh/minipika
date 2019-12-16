package org.laniakea.poseidon.model.service;

import org.laniakea.poseidon.framework.annotation.NotNull;
import org.laniakea.poseidon.framework.beans.BeansManager;
import org.laniakea.poseidon.framework.db.JdbcSupport;
import org.laniakea.poseidon.framework.sql.SQLBuilder;
import org.laniakea.poseidon.framework.sql.SQLConstruct;

import java.util.Map;

/**
 * @author 2BKeyboard
 * @version 1.0.0
 * @date 2019/11/27 17:33
 * @since 1.8
 */
public class UserService {

    private JdbcSupport jdbc = BeansManager.getBean("jdbc");

    public static void main(String[] args) {

        /*

            SELECT
            	a.*,
            	b.contract_name AS contract_order_currencyname,
            	b.contract_currencytype AS contract_order_currencytype,
            	c.contract_pl_closeprice AS contract_order_closeprice,
            	c.contract_pl_profit AS contract_order_profit,
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

        @NotNull
        String username = "keyboard";

        SQLBuilder builder = new SQLBuilder("findUserByName", new SQLConstruct() {
            @Override
            public void param(Map<String, Object> params) {
                params.put("username",username);
            }
        });

    }

}
