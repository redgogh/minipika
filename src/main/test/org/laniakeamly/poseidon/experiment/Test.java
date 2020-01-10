package org.laniakeamly.poseidon.experiment;

import org.laniakeamly.poseidon.framework.annotation.Column;
import org.laniakeamly.poseidon.framework.annotation.Model;
import org.laniakeamly.poseidon.framework.annotation.PrimaryKey;

/**
 * Create by 2BKeyboard on 2020/1/11 2:57
 */
@Model("te")
public class Test {

    @PrimaryKey
    @Column("int(11) not null")
    private Long id;

}
