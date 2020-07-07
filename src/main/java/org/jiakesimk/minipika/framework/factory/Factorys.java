package org.jiakesimk.minipika.framework.factory;

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
 * Creates on 2020/6/2.
 */

import org.jiakesimk.minipika.components.jdbc.*;
import org.jiakesimk.minipika.components.jdbc.transaction.JdbcTransaction;
import org.jiakesimk.minipika.components.jdbc.transaction.JdbcTransactionFactory;
import org.jiakesimk.minipika.components.jdbc.transaction.TransactionFactory;
import org.jiakesimk.minipika.framework.context.ContextManager;

/**
 * @author tiansheng
 */
public class Factorys {

  static {
    ContextManager.loadContext(); // 加载上下文
    BuiltinComponentFactory.components.put(NativeResultSet.class.getName(), forClass(ConstResultSet.class));
    BuiltinComponentFactory.components.put(NativeJdbcImpl.class.getName(), forClass(NativeJdbcImpl.class));
    BuiltinComponentFactory.components.put(JdbcTransaction.class.getName(), forClass(JdbcTransaction.class));
    BuiltinComponentFactory.components.put(TransactionFactory.class.getName(), forClass(JdbcTransactionFactory.class));
    BuiltinComponentFactory.components.put(Executor.class.getName(), forClass(SQLExecutor.class));
  }

  public static <T> T forClass(Class<T> clazz) {
    return BuiltinComponentFactory.factory.forClass(clazz);
  }

  public static <T> T forClass(Class<?> clazz, Class<?>[] types, Object... parameter) {
    return BuiltinComponentFactory.factory.forClass(clazz, types, parameter);
  }

}
