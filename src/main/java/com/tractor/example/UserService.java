package com.tractor.example;

import com.tractor.db.JdbcSupport;
import com.tractor.example.experiment.UserModel;

/**
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/27 17:33
 * @since 1.8
 */
public class UserService extends JdbcSupport {

    public UserModel findUserById(String id) {
        return queryForObject("select * from user_model where id = ?", UserModel.class, id);
    }

}
