package org.poseidon.experiment;

import lombok.Data;
import org.laniakeamly.poseidon.framework.annotation.model.Column;
import org.laniakeamly.poseidon.framework.annotation.model.Comment;
import org.laniakeamly.poseidon.framework.annotation.model.Model;
import org.laniakeamly.poseidon.framework.annotation.model.PrimaryKey;

/**
 * @author TianSheng
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

    @Column("varchar(255) not null")
    @Comment("产品名称")
    private String productName;

    @Column("varchar(255) not null")
    private String uuid;

    static String aa = "";
    final String bb = "";

}
