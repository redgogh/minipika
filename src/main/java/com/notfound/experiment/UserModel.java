package com.notfound.experiment;

import com.notfound.annotation.*;

/**
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/12 9:51
 * @since 1.8
 */
@Model("user_model")
public class UserModel {

    @PrimaryKey
    private Long id;
    // user_name
    @Column("len=255,default='王十三'")
    @Comment("用户名，不作为登陆账号。")
    private String userName;
    // google_email
    @Column("len=255,notnull")
    @Comment("Google邮箱")
    private String googleEmail;

}
