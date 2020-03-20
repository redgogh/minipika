package org.raniaia.poseidon.modules.log;

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
 * Creates on 2020/3/20 20:17
 */

import org.raniaia.poseidon.framework.context.module.ModuleRegister;
import org.raniaia.poseidon.framework.provide.module.Module;
import org.raniaia.poseidon.modules.log.slf4j.Slf4jAdapter;

import java.util.Map;

@Module
public class LogExport implements ModuleRegister {

    @Override
    public void register(Map<Class<?>, Object> modules) {
        modules.put(LogAdapter.class,new Slf4jAdapter());
    }
}
