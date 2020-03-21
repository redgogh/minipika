package org.keyboard;

import com.alibaba.fastjson.JSON;
import org.poseidon.experiment.ProductModel;
import org.junit.Test;
import org.raniaia.poseidon.framework.sql.xml.Parameter;
import org.raniaia.poseidon.framework.sql.xml.SqlExecute;
import org.raniaia.poseidon.framework.sql.xml.SqlMapper;
import org.raniaia.poseidon.framework.tools.POFUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Copyright: Create by tiansheng on 2019/12/24 1:27
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

    @Test
    public void query(){
        SqlExecute execute = mapper.build("findProduct", map -> {
            map.put("uuid","1a043717-195c-4a58-8137-3e2ccce67eaa0");
        });
        System.out.println(JSON.toJSONString(execute.queryForObject()));
    }

}
