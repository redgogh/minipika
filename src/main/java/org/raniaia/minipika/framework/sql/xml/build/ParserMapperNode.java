package org.raniaia.minipika.framework.sql.xml.build;

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

import org.raniaia.minipika.framework.sql.xml.node.XMLDynamicSqlNode;
import org.raniaia.minipika.framework.sql.xml.node.XMLMapperNode;
import org.raniaia.minipika.framework.sql.xml.parser.ReaderMapperXML;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 解析节点
 * @author tiansheng
 */
public class ParserMapperNode {

    private ParserCrudNode parseMapper = new ParserCrudNode();

    public Map<String,PrecompiledClass> readBuilderNode() {
        try {
            ReaderMapperXML readerBuilderXML = new ReaderMapperXML();
            List<XMLMapperNode> xmlBuilderNode = readerBuilderXML.parseXML();
            Map<String,PrecompiledClass> classes = new HashMap<>();
            for (XMLMapperNode mapperNode : xmlBuilderNode) {
                PrecompiledClass dc = new PrecompiledClass(mapperNode.getName());
                for (XMLDynamicSqlNode xmlDynamicSqlNode : mapperNode.getDynamicsSqlSet()) {
                    PrecompiledMethod pm = parseMapper.parse(xmlDynamicSqlNode, mapperNode);
                    dc.addPrecompiledMethod(pm);
                }
                classes.put(dc.getName(),dc);
            }
            return classes;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
