package org.recycle.build;

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
 * Creates on TODO DATE
 */

import lombok.Getter;
import lombok.Setter;
import org.raniaia.poseidon.framework.provide.ProvideVar;
import org.raniaia.poseidon.framework.tools.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright: Create by tiansheng on 2019/12/18 19:26
 */
public class PrecompiledClass {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String fullName;

    @Getter
    @Setter
    private boolean load = false;

    @Getter
    @Setter
    private Class<?> iClass;

    Map<String, PrecompiledMethod> methods = new HashMap(6);

    public PrecompiledClass() {
    }

    public PrecompiledClass(String name) {
        this.name = name;
        this.fullName = ProvideVar.CLASS_FULL_NAME.concat(name);
    }

    public PrecompiledMethod getPrecompiledMethod(String name) {
        PrecompiledMethod pm = methods.get(name);
        if (pm == null) {
            throw new NullPointerException(StringUtils.format("'{}' mapper is not found please check your mapper xml in mapper '{}'", name, this.name));
        }
        return pm;
    }

    public void addPrecompiledMethod(PrecompiledMethod method) {
        methods.put(method.getName(), method);
    }

}
