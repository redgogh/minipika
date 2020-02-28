package org.poseidon.experiment;

import org.laniakeamly.poseidon.framework.annotation.model.Column;
import org.laniakeamly.poseidon.framework.annotation.model.Comment;
import org.laniakeamly.poseidon.framework.annotation.model.Model;
import org.laniakeamly.poseidon.framework.annotation.model.PrimaryKey;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Copyright: Create by TianSheng on 2019/12/9 10:35
 */
@Data
@Model("wallet")
public class WalletModel {

    @PrimaryKey
    @Column("int(11) not null")
    private Long id;

    @Column("decimal(10,3) not null")
    @Comment("金额")
    private BigDecimal amount;

}
