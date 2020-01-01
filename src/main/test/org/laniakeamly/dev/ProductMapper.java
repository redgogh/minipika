package org.laniakeamly.dev;

import org.laniakeamly.poseidon.experiment.ProductModel1;

import java.util.List;

/**
 * Create by 2BKeyboard on 2019/12/28 17:28
 */
public interface ProductMapper {

    List<ProductModel1> findProductByUUID(String uuid);

}
