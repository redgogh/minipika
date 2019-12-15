package com.test;

/**
 * Create by 2BKeyboard on 2019/12/14 21:50
 */
public class LookGeneratorJavaCode {

    public static java.lang.String findUserByName(String username,String status) {
        java.lang.StringBuilder sql = new java.lang.StringBuilder();
        sql.append("SELECT a.*, b.contract_name AS contract_order_currencyname, b.contract_currencytype AS contract_order_currencytype, b.contract_pl_closeprice AS contract_order_closeprice, b.contract_pl_profit AS contract_order_profit FROM shiqi_contract_order a LEFT JOIN shiqi_contract b ON b.contract_key = a.contract_order_currencykey LEFT JOIN shiqi_contract_pl c ON c.contract_pl_ordernumber = a.contract_order_id WHERE a.contract_order_account = {{account}}");
        if (username == null) {
            sql.append("and order_username1 = {{username}}");
            sql.append("and order_username2 = {{username}}");
        }
        if (status == null) {
            sql.append("and a.contract_order_status > {{status}}");
        } else {
            sql.append("and a.contract_order_status > {{status}}");
        }


        sql.append("and order_currency_type = {{type}} and order_status = {{status}} ORDER BY a.contract_order_createtime DESC LIMIT {{limit 0 ',' 1}}");
        return sql.toString();
    }

    public static void main(String[] args) {
        System.out.println(findUserByName(null,null));
    }

}
