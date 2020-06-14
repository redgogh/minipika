package org.jiakesiws.minipika.framework.sql.xml.parser;

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

import org.jdom2.Content;
import org.jdom2.Element;
import org.jiakesiws.minipika.framework.sql.xml.node.XMLNode;

/**
 * 解析XML
 * 为了整理所以写了这么一个接口
 * @author tiansheng
 */
interface MapperLabelParserService {

    /**
     * 获取文本文件
     * @param content {@link Content}
     * @return JavaCode
     */
    XMLNode text(Content content);

    /**
     * 解析if标签
     * @param element  当前if标签所属的Element对象
     * @return JavaCode
     */
    XMLNode ifOrEels(Element element);

    /**
     * 解析choose标签并转换成JavaCode
     * @param element       choose标签的Element对象
     * @return JavaCode
     */
    XMLNode choose(Element element);

    /**
     * 解析foreach标签
     * @param element
     * @return
     */
    XMLNode foreach(Element element);

}
