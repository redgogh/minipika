package org.laniakeamly.poseidon;

import org.laniakeamly.poseidon.framework.beans.BeansManager;
import org.laniakeamly.poseidon.framework.db.JdbcBuilder;
import org.laniakeamly.poseidon.framework.sql.SqlBuilder;
import org.laniakeamly.poseidon.framework.sql.SqlMapper;
import org.laniakeamly.poseidon.framework.sql.SqlParameter;

import java.util.Map;

/**
 * Create by 2BKeyboard on 2019/12/17 11:37
 */
@SuppressWarnings("all")
public class SqlBuilderVerify {

    private static SqlBuilder sqlBuilder = new SqlBuilder("UserMapper");

    private static JdbcBuilder jdbcBuilder = BeansManager.getBean("jdbcBuilder");

    public static void main(String[] args) {
        String name = "keyboard";
        SqlMapper sqlMapper = sqlBuilder.buildMapper("findUserByName", new SqlParameter() {
            @Override
            public void set(Map<String, Object> params) {
                params.put("username",name);
            }
        });
        System.out.println(sqlMapper.getSql());
    }

}
