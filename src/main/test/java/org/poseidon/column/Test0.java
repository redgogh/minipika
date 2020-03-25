package org.poseidon.column;

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



import org.junit.Test;

import org.raniaia.poseidon.BeansManager;
import org.raniaia.poseidon.components.jdbc.JdbcSupport;
import org.raniaia.poseidon.components.model.database.ColumnPo;

import java.util.List;

/**
 * Copyright by tiansheng on 2020/2/15 1:47
 * @author tiansheng
 * @version 1.0.0
 * @since 1.8
 */
public class Test0 {

    JdbcSupport jdbc = BeansManager.get("jdbc");

    @Test
    public void getColumn() {
        List<ColumnPo> columnModels = jdbc.queryForList("show full columns from kkb_example_model", ColumnPo.class);
        System.out.println(columnModels.toString());
    }

}