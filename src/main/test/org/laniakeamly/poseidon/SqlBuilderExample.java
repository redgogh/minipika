package org.laniakeamly.poseidon;

import org.junit.Test;
import org.laniakeamly.poseidon.experiment.UserModel;
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
public class SqlBuilderExample {

    private SqlBuilder sqlBuilder = new SqlBuilder("userDao");
    JdbcBuilder jdbcBuilder = BeansManager.getBean("jdbcBuilder");

    public static void main(String[] args) {
        SqlBuilderExample sbe = new SqlBuilderExample();
        System.out.println(sbe.test("91341a60-d319-49af-bb70-fbbe6bef0411").toString());;
        System.out.println(sbe.test("a04a34da-fbcf-4345-ac60-4fb24a188d86").toString());;
    }

    public UserModel test(String uuid) {
        SqlMapper sqlMapper = sqlBuilder.buildMapper("findUserByUUID", new SqlParameter() {
            @Override
            public void set(Map<String, Object> params) {
                params.put("uuid", uuid);
            }
        });
        return jdbcBuilder.queryForObject(sqlMapper);
    }

}
