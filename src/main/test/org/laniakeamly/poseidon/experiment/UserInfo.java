package org.laniakeamly.poseidon.experiment;

import org.laniakeamly.poseidon.framework.annotation.Column;
import org.laniakeamly.poseidon.framework.annotation.Model;
import org.laniakeamly.poseidon.framework.annotation.PrimaryKey;

/**
 * Create by 2BKeyboard on 2020/1/17 14:01
 */
@Model(value = "user_info",increment = 4000)
public class UserInfo {

    @PrimaryKey
    @Column("int(11) not null")
    private int id;

    @Column("varchar(255) not null")
    private String name;

}
