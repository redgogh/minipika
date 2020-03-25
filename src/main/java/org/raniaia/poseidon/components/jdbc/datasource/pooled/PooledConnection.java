package org.raniaia.poseidon.components.jdbc.datasource.pooled;

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

import lombok.Getter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Pooled connection.
 *
 * @author tiansheng
 */
public class PooledConnection implements InvocationHandler {

    private static String CLOSE = "close";
    private static Class<?>[] IFACE = new Class[]{Connection.class};


    private boolean                     valid;
    @Getter
    private Connection                  realConnection;
    @Getter
    private PooledDataSource            dataSource;
    @Getter
    private Connection                  proxyConnection;

    public PooledConnection() {
    }

    public PooledConnection(Connection connection, PooledDataSource dataSource) {
        this.realConnection = connection;
        this.dataSource = dataSource;
        this.proxyConnection = (Connection)
                Proxy.newProxyInstance(Connection.class.getClassLoader(), IFACE, this);
    }

    /**
     * Verify that the connection works.
     */
    // 验证链接是否可以正常使用
    public boolean isValid() throws SQLException {
        return valid && (realConnection != null) && realConnection.isClosed();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        if (CLOSE.equals(methodName)) {
            // todo 归还链接
            return null;
        } else {
            return method.invoke(realConnection, args);
        }
    }

}
