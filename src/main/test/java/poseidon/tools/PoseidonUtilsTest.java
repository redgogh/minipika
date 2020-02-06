package java.poseidon.tools;

import org.junit.Test;
import java.poseidon.experiment.ProductModel;
import org.laniakeamly.poseidon.framework.tools.POFUtils;

/**
 * Copyright: Create by TianSheng on 2020/1/15 1:37
 */
public class PoseidonUtilsTest {

    @Test
    public void getModelFieldTest(){
        ProductModel productModel = new ProductModel();
        POFUtils.getModelField(productModel);
    }

}
