package org.minipika.node;

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



import org.junit.Test;
import org.jiakesiws.minipika.framework.sql.xml.node.XMLMapperNode;
import org.jiakesiws.minipika.framework.sql.xml.parser.ReaderMapperXML;

import java.util.List;

/**
 * Copyright: Create by tiansheng on 2019/12/20 20:49
 */
public class ReaderMapperXMLTest {

    @Test
    public void read() throws Exception {
        ReaderMapperXML readerMapperXML = new ReaderMapperXML();
        List<XMLMapperNode> xmlMapperNodes = readerMapperXML.parseXML();
        System.out.println();
    }

}
