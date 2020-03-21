package org.raniaia.poseidon;

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
 * Creates on 2020/3/21 14:59
 */

import org.raniaia.available.map.Maps;
import org.raniaia.available.reflect.Annotations;
import org.raniaia.available.reflect.Methods;
import org.raniaia.poseidon.framework.provide.component.Component;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author tiansheng
 */
public class RootContainer<K,V> {

    private Map<K, V>               roots                   = Maps.newHashMap();

    private Map<String, Class<?>>   components              = Maps.newHashMap();

    public void putComponents(Map<String, Class<?>> components) {
        this.components.putAll( components );
    }

    public void putComponents(List<Class<?>> components) {
        for (Class<?> component : components) {
            this.components.put( component.getInterfaces()[0].getSimpleName(), component );
        }
    }

    protected <T> T getRoots0(K name){
        return (T) roots.get(name);
    }

    public Class<?> getComponents0(String name) {
        return components.get(name);
    }

    public void submitBean(K key, V bean){
        roots.put(key, bean);
    }

}
