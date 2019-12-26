package org.laniakeamly.poseidon;

import org.laniakeamly.poseidon.experiment.UserModel;
import org.laniakeamly.poseidon.framework.beans.BeansManager;
import org.laniakeamly.poseidon.framework.db.JdbcSupport;

import java.util.*;

/**
 * Create by 2BKeyboard on 2019/12/20 10:46
 */
public class Test {

    private JdbcSupport jdbcSupport = BeansManager.getBean("jdbc");

    @org.junit.Test
    public void insertUserModel() {
        Map map = new HashMap();
        UserModel userModel1 = new UserModel();
        userModel1.setUserName("aaaaaaaaaaaaa1");
        userModel1.setGoogleEmail("aaaaaaaaaaaaa2");
        userModel1.setAddress("aaaaaaaaaaaaa3");
        userModel1.setUuid("aaaaaaaaaaaaa4");
        userModel1.setCreateTime(new Date());
        List<UserModel> list = new ArrayList<>();
        list.add(userModel1);
        map.put("users", list);
        java.util.List<org.laniakeamly.poseidon.experiment.UserModel> $user = (List<UserModel>) map.get("users");
        System.out.println();
    }

    @org.junit.Test
    public void test2() {
        String abc = " INSERT INTO `user_model`( `user_name`, `google_email`, `password`, `user_age`, `address`, `uuid`, `product_name`, `create_time`) VALUES ( ?, 'c28a7745-7508-48a0-820e-a5cd14748d24', '123456', 18, 'c28a7745-7508-48a0-820e-a5cd14748d24', 'c28a7745-7508-48a0-820e-a5cd14748d24', '敌敌畏', '2019-12-03 09:39:19' );  INSERT INTO `user_model`( `user_name`, `google_email`, `password`, `user_age`, `address`, `uuid`, `product_name`, `create_time`) VALUES ( ?, 'c28a7745-7508-48a0-820e-a5cd14748d24', '123456', 18, 'c28a7745-7508-48a0-820e-a5cd14748d24', 'c28a7745-7508-48a0-820e-a5cd14748d24', '敌畏;', '2019-12-03 09:39:19' );  ";
        jdbcSupport.update(abc, "abc", "cba");
    }

}
