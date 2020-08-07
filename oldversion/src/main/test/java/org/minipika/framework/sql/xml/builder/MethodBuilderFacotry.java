package framework.sql.xml.builder;

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



/**
 * Copyright: Create by 2B键盘 on 2019/12/13 23:59
 */
public interface MethodBuilderFacotry {

    /**
     * 创建方法
     *
     * @param       modify      修饰符,传入String,需要空格分隔例如："public {} static name"
     *                          其中 {} 代表返回对象的转移符
     * @param       args        modify中需要format的数据
     * @param       _return     返回对象,全路径
     *
     * @return      CodeBuilder
     */
    MethodBuilder createMethod(String modify,String _return,String... args);

}
