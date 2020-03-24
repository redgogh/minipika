package org.keyboard;

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

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright: Create by tiansheng on 2019/12/25 23:57
 */
public class MethodAndVariable {

    private Map<String,String> hashMap = new HashMap<>();
    {
        hashMap.put("username","张三");
    }

    @Test
    public void methodTest(){
        long startTime = System.currentTimeMillis();
        for(int i=0; i<100000000; i++){
            hashMap.get("username").concat("a");
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }

    @Test
    public void variableTest(){
        long startTime = System.currentTimeMillis();
        String value = hashMap.get("username");
        for(int i=0; i<100000000; i++){
            value.concat("a");
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }

}
