package org.raniaia.poseidon.framework.context;

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
 * Creates on 2020/3/20 23:30
 */

import org.raniaia.poseidon.framework.provide.Resource;

/**
 * Poseidon beans manager class, new the bean container must extends
 * this interface.
 *
 * All beans need to provide a method, and provide the method must annotated {@link Resource}.
 *
 * @author tiansheng
 */
public interface PoseidonBeansManager {

    /**
     * During initialization scan bean.
     */
    void initScanBean();

    /**
     * Scan beans and instantiate bean.
     */
    void scanBeans(Class<?>... classes);

    /**
     * Get bean.
     */
    <T> T getBean(String name);

    /**
     * Scan objects that need bean injecting.
     */
    Object inject(Object object);

}
