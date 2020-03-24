package org.raniaia.poseidon.framework.sql.xml.parser;

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
 * Creates on 2019/12/17.
 */

import org.jdom2.Element;
import org.raniaia.poseidon.framework.exception.runtime.ExpressionException;
import org.raniaia.poseidon.framework.provide.ProvideVar;
import org.raniaia.poseidon.framework.tools.StringUtils;

/**
 * 解析工具类
 * @author tiansheng
 */
public class XMLParserUtils {

    /**
     * 获取test标签中的内容
     * @param element
     * @return
     */
    public String getIfLabelTestAttribute(Element element){
        String test = element.getAttributeValue(ProvideVar.IF_TEST);
        if(StringUtils.isEmpty(test))
            throw new ExpressionException("tag: if label attribute test content cannot null");
        return test;
    }

    /**
     * 清除xml中不合法的换行
     * @param str
     * @return
     */
    public String trim(String str) {
        return StringUtils.trim(str);
    }

}
