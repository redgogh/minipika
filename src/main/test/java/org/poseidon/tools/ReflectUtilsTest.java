package org.poseidon.tools;

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



import lombok.Setter;
import org.junit.Test;
import org.raniaia.poseidon.framework.tools.ReflectUtils;

/**
 * Copyright: Create by tiansheng on 2019/12/20 11:55
 */
public class ReflectUtilsTest {

    @Setter
    private String name;

    @Test
    public void test1(){
        ReflectUtilsTest ru = new ReflectUtilsTest();
        ru.setName("zs");
        System.out.println(ReflectUtils.getMemberValue(ru,"name"));
    }

}
