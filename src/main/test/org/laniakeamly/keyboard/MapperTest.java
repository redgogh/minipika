package org.laniakeamly.keyboard;

import org.junit.Test;
import org.laniakeamly.poseidon.experiment.ProductModel;
import org.laniakeamly.poseidon.experiment.UserModel;
import org.laniakeamly.poseidon.framework.annotation.Valid;
import org.laniakeamly.poseidon.framework.sql.Parameter;
import org.laniakeamly.poseidon.framework.sql.SqlExecute;
import org.laniakeamly.poseidon.framework.sql.SqlMapper;
import org.laniakeamly.poseidon.framework.tools.PoseidonUtils;
import org.laniakeamly.poseidon.framework.tools.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Create by 2BKeyboard on 2019/12/24 1:27
 */
public class MapperTest {

    SqlMapper mapper = SqlMapper.getMapper("test");

    @Test
    public void builderSqlMapper(){

        List<ProductModel> list = new ArrayList<>();
        for(int i=0; i<5; i++) {
            ProductModel product = new ProductModel();
            product.setUuid(PoseidonUtils.uuid()+i);
            product.setProductName(PoseidonUtils.uuid()+i);
            list.add(product);
        }

        SqlExecute execute = mapper.build("addProduct", new Parameter() {
            @Override
            public void loader(Map<String, Object> map) {
                map.put("products",list);
            }
        });

        execute.update();

    }

}
