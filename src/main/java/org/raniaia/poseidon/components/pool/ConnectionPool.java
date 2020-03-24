package org.raniaia.poseidon.components.pool;

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

/*
 * Creates on 2019/12/9.
 */

import java.sql.Connection;

/**
 * 这个接口被实现后可以替换默认的连接池。 {@link org.raniaia.poseidon.components.pool.connection.ConnectionPool}
 *
 * 如果你不想使用默认自带的连接池，可以选择使用其他连接池实现该接口。
 *
 * this interface can implement.
 * implemented and config can replace default connection pool {@link org.raniaia.poseidon.components.pool.connection.ConnectionPool}
 *
 * if you don't want use default connection pool.
 *
 * @author tiansheng
 */
public interface ConnectionPool {

    /**
     * 获取连接
     */
    Connection getConnection();

    /**
     * 归还连接
     */
    void release(Connection connection);

}
