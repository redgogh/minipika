package org.poseidon.experiment;

import lombok.Data;
import org.laniakeamly.poseidon.framework.annotation.model.Column;
import org.laniakeamly.poseidon.framework.annotation.model.Model;
import org.laniakeamly.poseidon.framework.annotation.model.PrimaryKey;
import org.laniakeamly.poseidon.framework.annotation.model.Regular;

/**
 * Copyright: Create by TianSheng on 2020/1/11 2:57
 */
@Model("test_model")
@Data
public class TestModel {

    @PrimaryKey
    @Column("int(11) not null")
    private Long id;

    @Regular("email")
    @Column("varchar(255) not null")
    private String email;

}
