package org.raniaia.poseidon.modules.container;

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
 * Creates on 2020/3/20 22:39
 */

import org.raniaia.poseidon.framework.context.module.ModuleRegister;
import org.raniaia.poseidon.framework.provide.module.Module;
import org.raniaia.poseidon.modules.container.core.PoseidonContainerImpl;

import java.util.Map;

/**
 * @author tiansheng
 */
@Module
public class ContainerExport implements ModuleRegister {

    @Override
    public void register(Map<Class<?>, Object> modules) {
        modules.put(PoseidonContainer.class,new PoseidonContainerImpl());
    }

}
