package java.poseidon.experiment;

import org.laniakeamly.poseidon.framework.limit.Column;
import org.laniakeamly.poseidon.framework.limit.Model;
import org.laniakeamly.poseidon.framework.limit.PrimaryKey;

/**
 * Copyright: Create by TianSheng on 2020/1/17 14:01
 */
@Model(value = "user_info",increment = 4000)
public class UserInfo {

    @PrimaryKey
    @Column("int(11) not null")
    private int id;

    @Column("varchar(255) not null")
    private String name;

}
