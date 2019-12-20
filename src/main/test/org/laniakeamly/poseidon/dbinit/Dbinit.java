package org.laniakeamly.poseidon.dbinit;

import org.junit.Test;
import org.laniakeamly.poseidon.experiment.UserModel;
import org.laniakeamly.poseidon.framework.beans.BeansManager;
import org.laniakeamly.poseidon.framework.db.JdbcBuilder;
import org.laniakeamly.poseidon.framework.sql.SqlBuilder;
import org.laniakeamly.poseidon.framework.sql.SqlMapper;
import org.laniakeamly.poseidon.framework.sql.SqlParameter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Create by 2BKeyboard on 2019/12/20 10:07
 */
public class Dbinit {

    private SqlBuilder sqlBuilder = new SqlBuilder("init");
    private JdbcBuilder jdbcBuilder = BeansManager.getBean("jdbcBuilder");

    @Test
    public void insertUserModel(){
        List<UserModel> users = new ArrayList<>();

        UserModel userModel1 = new UserModel();
        userModel1.setUserName("aaaaaaaaaaaaa1");
        userModel1.setGoogleEmail("aaaaaaaaaaaaa2");
        userModel1.setAddress("aaaaaaaaaaaaa3");
        userModel1.setUuid("aaaaaaaaaaaaa4");
        userModel1.setCreateTime(new Date());

        UserModel userModel2 = new UserModel();
        userModel2.setUserName("bbbbbbbbbbbbbbbbbbb1");
        userModel2.setGoogleEmail("bbbbbbbbbbbbbbbbbbb2");
        userModel2.setAddress("bbbbbbbbbbbbbbbbbbb3");
        userModel2.setUuid("bbbbbbbbbbbbbbbbbbb4");
        userModel2.setCreateTime(new Date());

        users.add(userModel1);
        users.add(userModel2);

        SqlMapper sqlMapper = sqlBuilder.buildMapper("insertUserModel", new SqlParameter() {
            @Override
            public void set(Map<String, Object> params) {
                params.put("users",users);
            }
        });

        jdbcBuilder.update(sqlMapper);

    }

}
