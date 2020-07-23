package org.jiakesimk.minipika.framework.sql.xml.parser;

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

/*
 * Creates on 2019/12/16.
 */

import org.jdom2.Element;
import org.jiakesimk.minipika.framework.exception.BuilderXmlException;
import org.jiakesimk.minipika.framework.provide.ProvideVar;
import org.jiakesimk.minipika.framework.tools.StringUtils;

import java.util.List;

/**
 * 语法错误检测
 * @author tiansheng
 * @email jiakesiws@gmail.com
 */
public class GrammarCheck {

    /**
     * 检测choose标签是否满足规范
     * @param chooseChildren choose标签下的element
     * @param builderName      当前choose在哪个mapper下
     */
    public void chooseCheck(List<Element> chooseChildren, String builderName, String mapperName) {
        if (chooseChildren.size() <= 0)
            throw new BuilderXmlException("tag: the choose label must contain if label in mapper " + builderName + " mapper: " + mapperName);
        Element _if = chooseChildren.get(0);
        if (StringUtils.isEmpty(_if.getAttribute(ProvideVar.IF_TEST).getValue()))
            throw new BuilderXmlException("tag: choose in if attribute test cannot null in mapper " + builderName + " mapper: " + mapperName);
    }

}
