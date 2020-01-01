package org.laniakeamly.keyboard.framework;

import org.laniakeamly.poseidon.experiment.ProductModel;
import org.laniakeamly.poseidon.framework.tools.PoseidonUtils;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        ProductService service = new ProductService();

        ProductModel findProduct = service.findProduct(742,null,null);
        ProductModel byUUID = service.findProductByUUID("09a6a570-284b-4f15-b062-ef0fec073ba70");

        List<ProductModel> list = new ArrayList<>();
        for(int i=0; i<5; i++) {
            ProductModel product = new ProductModel();
            product.setUuid(PoseidonUtils.uuid()+i);
            product.setProductName(PoseidonUtils.uuid()+i);
            list.add(product);
        }
        int[] ints = service.addProducts(list);

        System.err.println("END");

    }

}
