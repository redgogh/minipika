package org.laniakeamly.keyboard.framework;

import org.laniakeamly.poseidon.experiment.ProductModel;
import org.laniakeamly.poseidon.framework.config.ManualConfig;
import org.laniakeamly.poseidon.framework.tools.PoseidonUtils;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        ProductService service = new ProductService();

        System.out.println(service.findProductname(1));

        System.err.println("END");

    }

}
