package org.statics;

/* ************************************************************************
 *
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
 *
 * ************************************************************************/



import org.junit.Test;

/**
 * Copyright by tiansheng on 2020/3/3 15:53
 * @author tiansheng
 * @version 1.0.0
 * @since 1.8
 */
public class Test01 {

    public static ObjectGc newObject() {
        return new ObjectGc();
    }

    public void create() {
        ObjectGc objectGc = newObject();
        System.out.println("对象创建成功 - " + objectGc);
    }

    @Test
    public void main(){
        create();
        // 执行gc
        System.gc();
        while (true);
    }

}
