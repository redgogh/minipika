package org.branch.datasource;

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
import org.raniaia.approve.components.jdbc.datasource.pooled.PooledConnection;
import org.raniaia.approve.components.jdbc.datasource.pooled.PooledDataSource;
import org.raniaia.approve.components.jdbc.datasource.unpooled.IDataSource;
import org.raniaia.approve.components.jdbc.datasource.unpooled.UnpooledDatasource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author tiansheng
 */
public class DataSourceTest {

    volatile long count = 0L;

    IDataSource iDataSource = new IDataSource(
            "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT",
            "com.mysql.cj.jdbc.Driver",
            "root","root"
    );

    @Test
    public void unpooled() throws SQLException {

        UnpooledDatasource unpooledDatasource = new UnpooledDatasource(iDataSource);

        Connection connection = unpooledDatasource.getConnection();
        System.out.println("AUTOCOMMIT: " + connection.getAutoCommit());

    }

    @Test
    public void pooled() throws SQLException, InterruptedException {
        PooledDataSource source = new PooledDataSource(iDataSource);
        for(int i=0; i<20000; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int i = 0; i < 10; i++) {
                            PooledConnection conn = source.popConnection();
                            conn.close();
                            count++;
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        System.out.println(source.getState().toString());
    }

}
