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
 * Creates on 2020/3/21.
 */

import org.raniaia.available.reflect.Annotations;
import org.raniaia.available.reflect.ClassUtils;
import org.raniaia.available.reflect.Fields;
import org.raniaia.available.reflect.Methods;
import org.raniaia.poseidon.framework.mapper.MapperInvocation;
import org.raniaia.poseidon.framework.provide.ProvideVar;
import org.raniaia.poseidon.framework.provide.Valid;
import org.raniaia.poseidon.framework.provide.component.Component;
import org.raniaia.poseidon.framework.sql.SqlMapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author tiansheng
 */
@SuppressWarnings("unchecked")
public abstract class AbstractContainer extends RootContainer<String, Object> {

    protected AbstractContainer() {

    }

    public AbstractContainer(Map<String, Class<?>> components) {
        putComponents(components);
    }

    public <T> T newInstance(Class<?> clazz) {
        Object instance = newInstance(clazz, null);
        return (T) inject(instance);
    }

    public <T> T newInstance(Class<?> clazz, Object... args) {
        Class<?>[] parametersTypes = null;
        if (args != null) {
            parametersTypes = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                parametersTypes[i] = args[i].getClass();
            }
        }
        //
        // If class is interface and class name equals mapper xml name.
        // Then think of class as a mapper interface.
        //
        if (clazz.isInterface()) {
            String classname = clazz.getSimpleName();
            if (SqlMapper.isMapper(classname)) {
                return (T) MapperInvocation.invoker(clazz);
            }
        }
        if (parametersTypes == null) {
            return (T) ClassUtils.newInstance(clazz);
        }
        Object instance = ClassUtils.newInstance(clazz, parametersTypes, args);
        return (T) inject(instance);
    }

    /**
     * Get bean by {@code Class} from {@link RootContainer}.
     *
     * @see RootContainer#getRoots0
     */
    protected <T> T get0(Class<?> clazz) {
        return get0(clazz.getInterfaces()[0].getSimpleName());
    }

    /**
     * Get bean by {@code String} from {@link RootContainer}.
     *
     * @see RootContainer#getRoots0
     */
    protected <T> T get0(String name) {
        Object instance = getRoots0(name);
        if (instance == null) {
            Class<?> component = getComponents0(name);
            if (component == null) return null;
            instance = ClassUtils.newInstance(component);
            submitBean(name, inject(instance));
        }
        return (T) instance;
    }

    public void loadComponents(Class<?>... statements) {
        for (Class<?> statement : statements) {
            Method[] methods = Methods.getDeclaredMethods(statement, true);
            for (Method method : methods) {
                Component component = Annotations.isAnnotation(method, Component.class);
                if (component != null) {
                    Object object = Methods.invoke(method);
                    submitBean(object.getClass().getInterfaces()[0].getSimpleName(), object);
                }
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
                    Class<?> componentClass = getComponents0(componentName);
                    if (componentClass == null) {
                        throw new NullPointerException("cannot found [" + componentName + "] component");
                    }
                    componentInstance = ClassUtils.newInstance(componentClass);
                    inject(componentInstance);
                }
                Fields.set(instance, componentInstance, field);
                submitBean(getComponentName(componentInstance.getClass()),
                        componentInstance);
            }
        }
        return instance;
    }

}
