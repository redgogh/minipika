package org.jiakesiws.minipika;

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

/**
 * @author 2B键盘
 * @email jiakesiws@gmail.com
 */
class Container extends AbstractContainer {

    private static Container container;

    public static Container getContainer(Class<?>[] components) {
        if (container == null) {
            container = new Container(components);
            ComponentScan componentScan = new ComponentScan();
            container.putComponents(componentScan.components());
        }
        return container;
    }

    private Container(){}

    private Container(Class<?>[] components) {
        loadComponents(components);
    }

    public <T> T get(String name) {
        return get0(name);
    }

    public <T> T get(Class<?> name) {
        return get0(name.getSimpleName());
    }

}
