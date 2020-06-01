package org.minipika.framework.sql.xml.builder;

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



/**
 * Copyright: Create by tiansheng on 2019/12/13 15:47
 */
public interface CodeBuilderFactory {

    ClassBuilder methodToClassBody();

    /**
     * 创建类的声明
     * @return
     */
    ClassBuilder createClassStatement();

    /**
     * 添加方法
     * @param methodBuilder 方法对象
     * @return CodeBuilder
     */
    ClassBuilder putMethod(MethodBuilder methodBuilder);

}
