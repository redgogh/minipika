package org.poseidon;

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

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright: Create by tiansheng on 2019/12/17 17:27
 */
public class Jdom {

    @Test
    public void parserNotXMLFile() throws JDOMException, IOException {

        File file = new File("F:\\1ABCDE_FUCKERF\\poseidon\\src\\main\\java\\org\\raniaia\\poseidon\\model\\builder\\user_mapper.iakea");

        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(file);
        Element rootElement = document.getRootElement();

        System.out.println();

    }

    @Test
    public void findUserByName1111() {
        Map map = new HashMap();
        map.put("age", 100810);
        StringBuilder sql = new StringBuilder();
        sql.append("select * from usaer where 1 = 1");
        if ((java.lang.Integer) map.get("age") < 100086) {
            System.out.println("age小于10086");
        } else {
            System.out.println("age大于10086");
        }
    }

}
