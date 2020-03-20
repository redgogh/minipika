package org.raniaia.poseidon.framework.provide.component;

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
 * Creates on 2020/3/20 17:09
 */

import org.raniaia.poseidon.framework.context.component.ModuleRegister;

import java.lang.annotation.*;

/**
 * The {@code Export} annotation is represents component export processor, all of component must
 * via this processor export component internal the class, then provide it to other modules.
 *
 * If you want exposed a component, you need write component exposed method. and exposed class
 * need extends {@link ModuleRegister}, because every component needs register self.
 *
 * As for registration logic, you can write your own. like A have more implement classes.
 *
 * like we have A provide, and A has multiple implementations. framework doesn't know which A
 * implementation of A you want to use. so need you write a like component description the class,
 * tell framework you want which use class.
 *
 * @author tiansheng
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Export {

    String value() default "export";

}
