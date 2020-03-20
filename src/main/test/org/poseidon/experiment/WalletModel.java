package org.poseidon.experiment;

import org.raniaia.poseidon.framework.provide.model.Column;
import org.raniaia.poseidon.framework.provide.model.Comment;
import org.raniaia.poseidon.framework.provide.model.Model;
import org.raniaia.poseidon.framework.provide.model.PrimaryKey;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Copyright: Create by tiansheng on 2019/12/9 10:35
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
