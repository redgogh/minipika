package org.laniakeamly.keyboard.framework;

import org.laniakeamly.poseidon.experiment.ProductModel;
import org.laniakeamly.poseidon.framework.config.ManualConfig;
import org.laniakeamly.poseidon.framework.tools.PoseidonUtils;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        ProductService service = new ProductService();

        ProductModel findProduct = service.findProduct(742,null,null);
        ProductModel byUUID = service.findProductByUUID("1f5ede35-052c-4e67-aea3-0e57f0e00a01");

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
