package org.minipika.service;

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




import org.raniaia.minipika.BeansManager;
import org.raniaia.minipika.components.jdbc.JdbcSupport;

/**
 * @author tiansheng
 * @version 1.0.0
 * @date 2019/11/27 17:33
 * @since 1.8
 */
public class UserService {

    private JdbcSupport jdbc = BeansManager.get("jdbc");

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

        String username = "keyboard";

    }

}
