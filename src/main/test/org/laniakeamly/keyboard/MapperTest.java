package org.laniakeamly.keyboard;

import org.junit.Test;
import org.laniakeamly.poseidon.experiment.ProductModel;
import org.laniakeamly.poseidon.framework.sql.xml.Parameter;
import org.laniakeamly.poseidon.framework.sql.xml.SqlExecute;
import org.laniakeamly.poseidon.framework.sql.xml.SqlMapper;
import org.laniakeamly.poseidon.framework.tools.POFUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Copyright: Create by TianSheng on 2019/12/24 1:27
 */
public class MapperTest {

    SqlMapper mapper = SqlMapper.getMapper("productService");

    @Test
    public void builderSqlMapper(){

        List<ProductModel> list = new ArrayList<>();
        for(int i=0; i<5; i++) {
            ProductModel product = new ProductModel();
            product.setUuid(POFUtils.uuid()+i);
            product.setProductName(POFUtils.uuid()+i);
            list.add(product);
        }

        SqlExecute execute = mapper.build("addProducts", new Parameter() {
            @Override
            public void loader(Map<String, Object> map) {
                map.put("products",list);
            }
        });

        execute.executeBatch();

    }

}
