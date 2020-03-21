package org.poseidon.tools;

import org.raniaia.poseidon.framework.tools.ModelUtils;
import org.poseidon.experiment.ProductModel;
import org.junit.Test;

/**
 * Copyright: Create by tiansheng on 2020/1/15 1:37
 */
public class PoseidonUtilsTest {

    @Test
    public void getModelFieldTest(){
        ProductModel productModel = new ProductModel();
        ModelUtils.getModelField(productModel);
    }

}
