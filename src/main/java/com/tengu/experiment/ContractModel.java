package com.tengu.experiment;

import com.tengu.annotation.*;

/**
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/12 16:59
 * @since 1.8
 */
@Model("contract")
public class ContractModel {

    @PrimaryKey
    @Column("int(11) not null")
    @Comment("主键")
    private Long id;

    @Column("varchar(255) not null")
    @Comment("产品名称")
    private String productName;

}
