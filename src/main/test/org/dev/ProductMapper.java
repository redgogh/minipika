package org.dev;

import org.poseidon.experiment.ProductModel;

import java.util.List;

/**
 * Copyright: Create by tiansheng on 2019/12/28 17:28
 */
public interface ProductMapper {

    List<ProductModel> findProductByUUID(String uuid);

}
