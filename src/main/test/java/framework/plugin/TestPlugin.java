package framework.plugin;

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
 * Creates on 2020/6/1.
 */

import org.raniaia.minipika.framework.plugins.Invocation;
import org.raniaia.minipika.framework.plugins.Interceptor;

import java.io.InputStream;

/**
 * @author tiansheng
 */
public class TestPlugin implements Interceptor {

  @Override
  public Object invocation(Invocation invocation, Object[] args) {
    return invocation.invoke(args);
  }

  @Override
  public InputStream pluginXMLConfigInputStream() {
    return null;
  }
}
