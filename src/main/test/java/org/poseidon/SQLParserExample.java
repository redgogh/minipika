package org.poseidon;

import net.sf.jsqlparser.JSQLParserException;
import org.raniaia.poseidon.framework.tools.SQLUtils;

/**
 * Copyright: Create by tiansheng on 2019/12/6 16:02
 */
public class SQLParserExample {

    static String sql = "select * from user_model as u,user_money as m left join product_model as p on u.name = p.name where id = 1";

    public static void main(String[] args) throws JSQLParserException {
        System.out.println(SQLUtils.getSQLTables(sql));
    }

}
