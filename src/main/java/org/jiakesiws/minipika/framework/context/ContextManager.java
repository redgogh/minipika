package org.jiakesiws.minipika.framework.context;

/* ************************************************************************
 *
 * Copyright (C) 2020 2B键盘 All rights reserved.
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
 *
 * ************************************************************************/

/*
 * Creates on 2020/7/2.
 */

import org.jiakesiws.minipika.components.cache.Cache;
import org.jiakesiws.minipika.components.cache.FetchCache;
import org.jiakesiws.minipika.components.configuration.XMLConfig;
import org.jiakesiws.minipika.components.jdbc.*;
import org.jiakesiws.minipika.components.jdbc.transaction.JdbcTransaction;
import org.jiakesiws.minipika.components.jdbc.transaction.JdbcTransactionFactory;
import org.jiakesiws.minipika.components.jdbc.transaction.TransactionFactory;
import org.jiakesiws.minipika.framework.factory.ComponentContainer;
import org.jiakesiws.minipika.framework.factory.ClassLoaders;
import org.jiakesiws.minipika.framework.strategy.FindStrategy;
import org.jiakesiws.minipika.components.configuration.XMLConfigBuilder;

/**
 * 上下文管理
 *
 * @author 2B键盘
 * @email jiakesiws@gmail.com
 */
public class ContextManager
{

  public static void loadContext()
  {
    XMLConfigBuilder configBuilder = new XMLConfigBuilder();
    configBuilder.load(FindStrategy.getConfigInputStream());
    ComponentContainer.getComponents().put(XMLConfig.class.getName(), configBuilder.getConfig());
    ComponentContainer.getComponents().put(NativeResultSet.class.getName(), ConstResultSet.class);
    ComponentContainer.getComponents().put(Cache.class.getName(), ClassLoaders.forClass(FetchCache.class));
    ComponentContainer.getComponents().put(NativeJdbc.class.getName(), ClassLoaders.forClass(NativeJdbcImpl.class));
    ComponentContainer.getComponents().put(JdbcTransaction.class.getName(), ClassLoaders.forClass(JdbcTransaction.class));
    ComponentContainer.getComponents().put(TransactionFactory.class.getName(), ClassLoaders.forClass(JdbcTransactionFactory.class));
    ComponentContainer.getComponents().put(Executor.class.getName(), ClassLoaders.forClass(SQLExecutor.class));
  }

}
