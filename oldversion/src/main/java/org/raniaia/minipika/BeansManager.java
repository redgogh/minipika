package org.jiakesimk.minipika;

/* ************************************************************************
 *
 * Copyright (C) 2020 2B键盘 All rights reserved.
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
 *
 * ************************************************************************/

/*
 * Creates on 2020/3/21.
 */

import org.jiakesimk.minipika.components.MyComponents;
import org.jiakesimk.minipika.components.jdbc.NativeResult;
import org.jiakesimk.minipika.components.jdbc.NativeResultMysql;
import org.jiakesimk.minipika.framework.sql.SqlMapper;

/**
 * Bean container management object, all beans will be created
 * from this object.
 *
 * @author 2B键盘
 * @email jiakesiws@gmail.com
 */
@SuppressWarnings("unchecked")
public class BeansManager {

    private static Container container = Container.getContainer(new Class[]{MyComponents.class});

    /**
     * Get bean by {@code Class}.
     */
    public static <T> T get(Class name) {
        return container.get(name);
    }

    /**
     * Get bean by {@code String}.
     */
    public static <T> T get(String name) {
        return container.get(name);
    }

    /**
     * Handle objects to container management.
     */
    public static void submitBean(String name, Object value) {
        container.submitBean(name, value);
    }

    /**
     * Creates a new object, during create will minipika bean into the object.
     * This {@code #newInstance} method will calling No-argument construct.
     */
    public static <T> T newInstance(Class<?> clazz) {
        return container.newInstance(clazz);
    }

    /**
     * Creates a new object, during create will minipika bean into the object.
     * This {@code #newInstance} method will calling argument construct.
     *
     * @param args construct parameters.
     * @see SqlMapper#getMapper
     */
    public static <T> T newInstance(Class<?> clazz, Object... args) {
        return container.newInstance(clazz, args);
    }

    /**
     * Creates a new {@link NativeResult} instance.
     */
    public static NativeResult newNativeResult() {
        return new NativeResultMysql();
    }

}
