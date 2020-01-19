package org.laniakeamly.keyboard.framework;

import org.laniakeamly.poseidon.experiment.ProductModel;
import org.laniakeamly.poseidon.framework.sql.Parameter;
import org.laniakeamly.poseidon.framework.sql.SqlMapper;

import java.util.List;
import java.util.Map;

public class ProductService {

    private SqlMapper mapper = SqlMapper.getMapper("productService");

    public ProductModel findProductByUUID(String uuid) {
        return mapper.build("findProductByUUID", new Parameter() {
            @Override
            public void loader(Map<String, Object> map) {
                map.put("uuid", uuid);
            }
        }).queryForObject();
    }

    public ProductModel findProduct(int id, String productName, String uuid) {
        return mapper.build("findProduct", new Parameter() {
            @Override
            public void loader(Map<String, Object> map) {
                map.put("id",id);
                map.put("uuid",uuid);
                map.put("productName",productName);
            }
        }).queryForObject();
    }

    public int[] addProducts(List<ProductModel> products){
        return mapper.build("addProducts", new Parameter() {
            @Override
            public void loader(Map<String, Object> map) {
                map.put("products",products);
            }
        }).executeBatch();
    }

    public String findProductname(int id) {
        return mapper.build("findProductName", new Parameter() {
            @Override
            public void loader(Map<String, Object> map) {
                map.put("id",id);
            }
        }).queryForObject().toString();
    }


}
