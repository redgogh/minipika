package org.poseidon.experiment;

import lombok.Data;
import org.raniaia.poseidon.framework.annotation.model.Column;
import org.raniaia.poseidon.framework.annotation.model.Model;
import org.raniaia.poseidon.framework.annotation.model.PrimaryKey;
import org.raniaia.poseidon.framework.annotation.model.Regular;

/**
 * Copyright: Create by TianSheng on 2020/1/17 14:01
 */
@Data
@Model(value = "user_info",increment = 4000)
public class UserInfo {

    @PrimaryKey
    @Column("int(11) not null")
    private int id;

    @Column("varchar(255) not null")
    private String name;

    @Regular("email")
    @Column("varchar(255) not null")
    private String email;

    public UserInfo() {
    }

    public UserInfo(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
