package org.minipika.javassist;

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



import javassist.CannotCompileException;
import javassist.CtClass;
import org.junit.Test;
import org.raniaia.minipika.components.pool.MinipikaClassPool;

/**
 * Copyright: Create by tiansheng on 2019/12/19 16:07
 */
public class CreateCtClass {

    @Test
    public void create() throws CannotCompileException {
        MinipikaClassPool pool = new MinipikaClassPool(true);
        CtClass[] ctClasses = pool.getCtClassArray("org.minipika.experiment");
        System.out.println();
    }

}
