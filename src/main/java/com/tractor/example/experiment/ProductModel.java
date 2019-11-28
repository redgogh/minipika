package com.tractor.example.experiment;

import com.tractor.annotation.*;
import lombok.Data;

/**
 * @author 2Bkeyboard
 * @version 1.0.0
 * @date 2019/11/22 16:41
 * @since 1.8
 */
@Data
@Model(value = "product_model")
public class ProductModel {

    @Column("int(11) not null")
    @PrimaryKey
    private Long id;

    @Column("varchar(50) not null")
    @Comment("产品名称")
    private String productName;

    @Column("varchar(255) not null")
    private String uuid;

}
