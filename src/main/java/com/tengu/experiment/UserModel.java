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

    // user_name
    @Column("varchar(255) not null")
    @Comment("用户账号")
    private String userName;

    // password
    @Column("varchar(255) not null default '123456'")
    @Comment("用户密码")
    private String password;

    @Column("int(11) not null default 18")
    @Comment("用户年龄")
    private Integer userAge;

    @Column("varchar(255) not null default '地球'")
    @Comment("地址")
    private String address;

    // google_email
    @Column("varchar(255) not null default 'NoEmail'")
    @Comment("Google邮箱")
    private String googleEmail;

    // product_name
    @Column("varchar(255) not null default '敌敌畏'")
    @Comment("产品名称")
    private String productName;

    @Ignore
    private String name;

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", userAge=" + userAge +
                ", address='" + address + '\'' +
                ", googleEmail='" + googleEmail + '\'' +
                ", productName='" + productName + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
