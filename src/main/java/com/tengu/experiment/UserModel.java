package com.tengu.experiment;

import com.tengu.annotation.*;

/**
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/12 9:51
 * @since 1.8
 */
@Model("user_model")
public class UserModel {

    @PrimaryKey
    @Column("int(11) not null")
    private Long id;

    // user_name\
    @Column("varchar(255) not null")
    @Comment("用户名，不作为登陆账号。")
    private String userName;

    // google_email
    @Column("varchar(255) not null default 'NoEmail'")
    @Comment("Google邮箱")
    private String googleEmail;

    @Ignore
    private String name;

}
