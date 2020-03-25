package org.branch.jdbc;

/*
 * Copyright (C) 2020 tiansheng All rights reserved.
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

/*
 * Creates on 2020/3/25.
 */

import org.junit.Test;
import org.raniaia.poseidon.components.jdbc.datasource.unpooled.IDataSource;
import org.raniaia.poseidon.components.jdbc.datasource.unpooled.UnpooledDatasource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author tiansheng
 */
public class UnpooledDataSourceTest {

    @Test
    public void connection() throws SQLException {

        IDataSource iDataSource = new IDataSource(
                "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT",
                "com.mysql.cj.jdbc.Driver",
                "root","root"
        );

        UnpooledDatasource unpooledDatasource = new UnpooledDatasource(iDataSource);

        Connection connection = unpooledDatasource.getConnection();
        System.out.println("AUTOCOMMIT: " + connection.getAutoCommit());

    }

}
