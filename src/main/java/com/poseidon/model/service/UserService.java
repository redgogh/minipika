package com.poseidon.model.service;

import com.poseidon.framework.db.JdbcSupport;
import com.poseidon.model.experiment.UserModel;
import com.poseidon.framework.tools.PoseidonUtils;

import java.util.Date;

/**
 * @author 2BKeyboard
 * @version 1.0.0
 * @date 2019/11/27 17:33
 * @since 1.8
 */
public class UserService extends JdbcSupport {

    public UserModel findUserById(String id) {
        return queryForObject("select * from user_model where id = ?", UserModel.class, id);
    }

    public Long insert(){
        UserModel userModel = new UserModel();
        userModel.setUserName(PoseidonUtils.uuid());
        userModel.setUuid(PoseidonUtils.uuid());
        userModel.setCreateTime(new Date());
        return (long) insert(userModel);
    }

}
