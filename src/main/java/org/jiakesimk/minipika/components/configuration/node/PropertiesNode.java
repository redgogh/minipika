package org.jiakesimk.minipika.components.configuration.node;

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
 * Creates on 2020/6/1.
 */

import org.jiakesimk.minipika.components.configuration.wrapper.ElementWrapper;
import org.jiakesimk.minipika.framework.utils.StringUtils;

import java.util.List;
import java.util.Properties;

/**
 * @author tiansheng
 * @email jiakesiws@gmail.com
 */
public class PropertiesNode implements ElementParser {

  private Properties properties;

  @Override
  public void parse(ElementWrapper element) {
    List<ElementWrapper> children = element.getChildren();
    if (children != null && !children.isEmpty()) {
      properties = new Properties();
      for (ElementWrapper child : children) {
        String key = child.getName();
        String value = child.getText();
        // 如果key和value都不等于空的话那么将属性设置到系统的properties对象中
        if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value)) {
          System.setProperty(key, value);
          properties.put(key, value);
        }
      }
    }
  }

}
