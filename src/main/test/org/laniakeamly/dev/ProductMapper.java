package org.laniakeamly.dev;

import org.laniakeamly.poseidon.experiment.ProductModel;

import java.util.List;

/**
 * Copyright: Create by 2BKeyboard on 2019/12/28 17:28
 */
public interface ProductMapper {

    List<ProductModel> findProductByUUID(String uuid);

}
