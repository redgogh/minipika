package org.maitreya.poseidon.model.experiment;

import org.maitreya.poseidon.framework.annotation.Column;
import org.maitreya.poseidon.framework.annotation.Comment;
import org.maitreya.poseidon.framework.annotation.Model;
import org.maitreya.poseidon.framework.annotation.PrimaryKey;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Create by 2BKeyboard on 2019/12/9 10:35
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
