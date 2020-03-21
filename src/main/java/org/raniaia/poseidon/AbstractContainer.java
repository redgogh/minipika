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
 * Creates on 2020/3/21 14:11
 */

import org.raniaia.available.reflect.Annotations;
import org.raniaia.available.reflect.Classes;
import org.raniaia.available.reflect.Fields;
import org.raniaia.available.reflect.Methods;
import org.raniaia.poseidon.components.log.LogAdapter;
import org.raniaia.poseidon.framework.provide.ProvideVar;
import org.raniaia.poseidon.framework.provide.Valid;
import org.raniaia.poseidon.framework.provide.component.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author tiansheng
 */
public abstract class AbstractContainer extends RootContainer<String, Object> {

    protected AbstractContainer() {

    }

    public AbstractContainer(Map<String, Class<?>> components) {
        putComponents(components);
    }

    public <T> T newInstance(Class<?> clazz) {
        Object instance = Classes.newInstance(clazz);
        return (T) inject(instance);
    }

    protected <T> T get0(Class<?> clazz){
        return get0(clazz.getInterfaces()[0].getSimpleName());
    }

    protected <T> T get0(String name) {
        Object instance = getRoots0(name);
        if (instance == null) {
            instance = Classes.newInstance(getComponents0(name));
            submitBean(name, inject(instance));
        }
        return (T) instance;
    }

    public void loadComponents(Class<?> statement) {
        Method[] methods = Methods.getDeclaredMethods(statement, true);
        for (Method method : methods) {
            Component component = Annotations.isAnnotation(method, Component.class);
            if (component != null) {
                Object object = Methods.invoke(method);
                submitBean(object.getClass().getInterfaces()[0].getSimpleName(), object);
            }
        }
    }

    /**
     * inject object.
     */
    protected Object inject(Object instance) {
        Class<?> clazz = instance.getClass();
        Field[] fields = Fields.getDeclaredFields(clazz, true);
        for (Field field : fields) {
            Valid valid = Annotations.isAnnotation(field, Valid.class);
            if (valid != null) {
                String componentName = field.getType().getSimpleName();
                Object componentInstance = getRoots0(componentName);
                if (componentInstance == null) {
                    // if component is Log component.
                    if (ProvideVar.LOG_COMPONENT.equals(componentName)) {
                        LogAdapter logAdapter = (LogAdapter)
                                Classes.newInstance(getComponents0(ProvideVar.LOG_ADAPTER_COMPONENT));
                        componentInstance = logAdapter.getLog(instance.getClass());
                    } else {
                        Class<?> componentClass = getComponents0(componentName);
                        if (componentClass == null) {
                            throw new NullPointerException("cannot found [" + componentName + "] component");
                        }
                        componentInstance = Classes.newInstance(componentClass);
                    }
                    inject(componentInstance);
                }
                Fields.set(instance, componentInstance, field);
                submitBean(componentInstance.getClass().getInterfaces()[0].getSimpleName(),
                        componentInstance);
            }
        }
        return instance;
    }

}
