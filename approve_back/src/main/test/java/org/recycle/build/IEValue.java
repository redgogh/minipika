package org.recycle.build;

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


import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

/**
 * if else value
 * Copyright: Create by tiansheng on 2019/12/18 3:33
 */
public class IEValue {

    @Getter
    @Setter
    List<String> tests = new LinkedList<>();

    @Getter
    List<String> ifContent = new LinkedList<>();

    @Getter
    List<String> elseContent = new LinkedList<>();

    public void addTest(String test){
        tests.add(test);
    }

    public void addIfContent(String content){
        ifContent.add(content);
    }

    public void addElseContent(String content){
        elseContent.add(content);
    }

}
