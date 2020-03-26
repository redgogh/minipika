package org.keyboard;

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



import com.alibaba.fastjson.JSON;
import org.approve.experiment.ProductModel;
import org.junit.Test;
import org.raniaia.approve.framework.sql.Parameter;
import org.raniaia.approve.framework.sql.SqlExecute;
import org.raniaia.approve.framework.sql.SqlMapper;
import org.raniaia.approve.framework.tools.POFUtils;

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
