package org.jiakesiws.minipika.framework.sql.xml.node;

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

/*
 * Creates on 2019/12/17.
 */

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

/**
 * @author 2B键盘
 * @email jiakesiws@gmail.com
 */
@Getter
@Setter
public class XMLDynamicSqlNode {

    private String name;
    private String type;
    private String result;

    private List<XMLNode> nodes = new LinkedList<>();

    public void addNode(XMLNode node){
        nodes.add(node);
    }

}
