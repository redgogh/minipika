package org.keyboard.framework;

/*
 * Copyright (C) 2020 Tiansheng All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



import org.poseidon.experiment.ProductModel;
import org.raniaia.approve.framework.sql.Parameter;
import org.raniaia.approve.framework.sql.SqlMapper;

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
