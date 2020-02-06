package org.laniakeamly.poseidon.tools;

import org.junit.Test;
import org.laniakeamly.poseidon.experiment.ProductModel;
import org.laniakeamly.poseidon.framework.tools.PoseidonUtils;

/**
 * Copyright: Create by 2BKeyboard on 2020/1/15 1:37
 */
public class PoseidonUtilsTest {

    @Test
    public void getModelFieldTest(){
        ProductModel productModel = new ProductModel();
        PoseidonUtils.getModelField(productModel);
    }

}
