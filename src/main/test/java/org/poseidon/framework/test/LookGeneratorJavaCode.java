package org.poseidon.framework.test;

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

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright: Create by tiansheng on 2019/12/14 21:50
 */
public class LookGeneratorJavaCode {

    public static void main(String[] args) {
        System.out.println(findUserByName());

    }

    public static Object findUserByName(){
        Map<String,Object> map = new HashMap<>();
        map.put("age1",20);
        map.put("age2",30);
        Object age2 = map.get("age2");
        return null;
    }

}