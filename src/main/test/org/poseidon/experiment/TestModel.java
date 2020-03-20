package org.poseidon.experiment;

import lombok.Data;
import org.raniaia.poseidon.framework.provide.model.Column;
import org.raniaia.poseidon.framework.provide.model.Model;
import org.raniaia.poseidon.framework.provide.model.PrimaryKey;
import org.raniaia.poseidon.framework.provide.model.Regular;

/**
 * Copyright: Create by tiansheng on 2020/1/11 2:57
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
