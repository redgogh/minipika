package com.tractor.example.service;

import com.tractor.db.JdbcSupport;
import com.tractor.example.experiment.UserModel;
import com.tractor.tools.TractorUtils;

import java.util.Date;

/**
 * @author 2Bkeyboard
 * @version 1.0.0
 * @date 2019/11/27 17:33
 * @since 1.8
 */
public class UserService extends JdbcSupport {

    public UserModel findUserById(String id) {
        return queryForObject("select * from user_model where id = ?", UserModel.class, id);
    }

    public Long findUserCount(){
        return count("user_model");
    }

    public Long insert(){
        UserModel userModel = new UserModel();
        userModel.setUserName(TractorUtils.uuid());
        userModel.setUuid(TractorUtils.uuid());
        userModel.setCreateTime(new Date());
        return (long) insert(userModel);
    }

}
