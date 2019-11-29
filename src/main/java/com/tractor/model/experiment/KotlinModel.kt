package com.tractor.model.experiment

import com.tractor.framework.annotation.Column
import com.tractor.framework.annotation.Comment
import com.tractor.framework.annotation.Model

@Model(value = "k_user_model")
class KotlinModel {

    @Column("varchar(255) not null")
    @Comment("用户名")
    var username: String? = null

    @Column("varchar(255) not null")
    @Comment("密码")
    var password: String? = null

}