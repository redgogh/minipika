package org.raniaia.poseidon.components.log.slf4j;

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
 * Creates on 2019/12/17 18:29
 */

import org.raniaia.poseidon.components.log.LogAdapter;
import org.raniaia.poseidon.components.log.Log;
import org.raniaia.poseidon.framework.provide.component.Component;

/**
 * @author tiansheng
 */
@Component
public class Slf4jAdapter implements LogAdapter {

    @Override
    public Log getLog(String key) {
        return new Slf4jLog(org.slf4j.LoggerFactory.getLogger(key));
    }

    @Override
    public Log getLog(Class<?> key) {
        return new Slf4jLog(org.slf4j.LoggerFactory.getLogger(key));
    }

}
