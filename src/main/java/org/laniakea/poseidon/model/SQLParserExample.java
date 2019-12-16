package org.laniakea.poseidon.model;

import org.laniakea.poseidon.framework.tools.PoseidonUtils;
import net.sf.jsqlparser.JSQLParserException;

/**
 * Create by 2BKeyboard on 2019/12/6 16:02
 */
public class SQLParserExample {

    static String sql = "select * from user_model as u,user_money as m left join product_model as p on u.name = p.name where id = 1";

    public static void main(String[] args) throws JSQLParserException {
        System.out.println(PoseidonUtils.getSQLTables(sql));
    }

}
