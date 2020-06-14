package org.jiakesimk.minipika.framework.sql.xml.node;

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

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tiansheng
 */
@Getter
public class XMLNode {

    private String name;
    private String content;
    @Setter
    private XMLNode parent;
    private List<XMLNode> children;
    private Map<String, String> attributes = new LinkedHashMap<>(2);

    public XMLNode() {
    }

    public XMLNode(String name) {
        this.name = name;
    }

    public XMLNode(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public void addAttribute(String key, String value) {
        attributes.put(key, value);
    }

    public String getAttribute(String name) {
        return attributes.get(name);
    }

    public void addChild(XMLNode node) {
        if (children == null) {
            children = new ArrayList<>();
        }
        node.setParent(this);
        children.add(node);
    }

}
