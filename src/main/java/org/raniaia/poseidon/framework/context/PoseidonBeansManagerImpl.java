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
 * Creates on 2020/3/20 23:34
 */

import org.raniaia.available.list.Lists;
import org.raniaia.available.reflect.Methods;
import org.raniaia.poseidon.framework.provide.Resource;
import org.raniaia.poseidon.framework.tools.Maps;
import org.raniaia.poseidon.framework.tools.StringUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author tiansheng
 */
@SuppressWarnings("unchecked")
public class PoseidonBeansManagerImpl implements PoseidonBeansManager {

    private Map<String, Object> beans = Maps.newHashMap();

    public PoseidonBeansManagerImpl() {
        initScanBean();
    }

    @Override
    public void initScanBean() {
        scanBeans(MyBeans.class);
    }

    @Override
    public void scanBeans(Class<?>... classes) {
        List<Method> methods = Lists.newArrayList();
        for (Class<?> target : classes) {
            Method[] methods0 = Methods.getMethodsByAnnotations(target, new Class[]{Resource.class});
            methods.addAll(Lists.newArrayList(methods0));
        }
        //
        // ### ### ### ###
        //
        for (Method method : methods) {
            Resource resource = Methods.isAnnotation(method, Resource.class);
            Object object = Methods.invoke(method);
            String name = resource.name();
            if (StringUtils.isEmpty(name)) {
                name = object.getClass().getSimpleName();
                name = StringUtils.lowerCase(name, 1);
            }
            beans.put(name, object);
        }
        System.out.println();
    }

    @Override
    public <T> T getBean(String name) {
        return (T) beans.get(name);
    }

    @Override
    public Object inject(Object object) {
        return null;
    }

    public static void main(String[] args) {
        new PoseidonBeansManagerImpl();
    }

}
