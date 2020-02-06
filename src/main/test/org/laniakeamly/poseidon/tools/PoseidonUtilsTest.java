package org.laniakeamly.poseidon.tools;

import org.junit.Test;
import org.laniakeamly.poseidon.experiment.ProductModel;
import org.laniakeamly.poseidon.framework.tools.PofUtils;

/**
 * Copyright: Create by TianSheng on 2020/1/15 1:37
 */
public class PoseidonUtilsTest {

    @Test
    public void getModelFieldTest(){
        ProductModel productModel = new ProductModel();
        PofUtils.getModelField(productModel);
    }

}
