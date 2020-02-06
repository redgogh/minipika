package java.dev;

import java.poseidon.experiment.ProductModel;

import java.util.List;

/**
 * Copyright: Create by TianSheng on 2019/12/28 17:28
 */
public interface ProductMapper {

    List<ProductModel> findProductByUUID(String uuid);

}
