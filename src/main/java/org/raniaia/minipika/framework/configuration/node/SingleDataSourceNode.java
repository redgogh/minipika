package org.raniaia.minipika.framework.configuration.node;

/*
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
 */

/*
 * Creates on 2020/6/1.
 */

import lombok.Getter;
import lombok.Setter;
import org.jdom2.Attribute;
import org.jdom2.Element;
import org.raniaia.minipika.framework.exception.XMLParseException;
import org.raniaia.minipika.framework.util.Maps;
import org.raniaia.minipika.framework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 单个数据源节点下的数据, 比如master、slaves
 *
 * @author tiansheng
 */
@Getter
@Setter
public class SingleDataSourceNode implements ElementParser {

  private String url;
  private String username;
  private String password;
  private String driver;
  private Map<String, String> urlParam = Maps.newHashMap();

  private DataSourceTask task;
  private DatabaseSupport type;

  static final String URL = "url";
  static final String USERNAME = "username";
  static final String PASSWORD = "password";
  static final String DRIVER = "driver";
  static final String VALUE = "value";
  static final String TYPE = "type";
  static final String TASK = "task";

  @Override
  public void parse(Element element) {
    // 解析type和task属性
    setting(element);
    // 解析URL属性
    Element urlElement = element.getChild(URL);
    checkUndefine(urlElement, URL);
    parseURLElementNode(urlElement);
    // 解析username
    Element usernameElement = element.getChild(USERNAME);
    Attribute usernameAttribute = usernameElement.getAttribute(VALUE);
    if (usernameAttribute == null) {
      throw new XMLParseException("Error username node of datasource node undefine.");
    }
    username = StringUtils.requireNonNull(usernameAttribute.getValue(),
            "Error value of username node cannot empty.", XMLParseException.class);
    // 解析password， password可以为NULL
    Element passwordElement = element.getChild(PASSWORD);
    checkUndefine(passwordElement, PASSWORD);
    if (passwordElement != null) {
      Attribute passwordAttribute = passwordElement.getAttribute(VALUE);
      if (passwordAttribute != null && StringUtils.isNotEmpty(passwordAttribute.getValue())) {
        password = passwordAttribute.getValue();
      }
    }
    // 解析driver
    Element driverElement = element.getChild("driver");
    checkUndefine(driverElement, DRIVER);
    Attribute attribute = driverElement.getAttribute(VALUE);
    driver = attribute.getValue();
    // 拼接URL参数
    buildURL();
  }

  private void parseURLElementNode(Element urlElement) {
    Attribute urlAttribute = urlElement.getAttribute(VALUE);
    if (urlAttribute == null) {
      String def = XMLParseException.buildTrack(urlElement);
      throw new XMLParseException("Error the url node of datasource require " +
              "value attribute in node track " + def + ". " + "Cause: value is required.");
    }
    String urlValue = urlAttribute.getValue();
    if (StringUtils.isEmpty(urlValue)) {
      String def = XMLParseException.buildTrack(urlElement);
      throw new XMLParseException("Error the url node of datasource the value attribute " +
              "value cannot null in node track " + def + ". " + "Cause: value is required.");
    }
    url = urlValue;
    // 判断URL节点下是否存在子节点
    List<Element> urlElementParam = urlElement.getChildren("property");
    if (urlElementParam != null && !urlElementParam.isEmpty()) {
      for (Element param : urlElementParam) {
        String def = XMLParseException.buildTrack(urlElement);
        Attribute keyAttribute = Objects.requireNonNull(param.getAttribute("key"),
                "Error attribute key not obtained in " + def + ".");
        Attribute valueAttribute = Objects.requireNonNull(param.getAttribute(VALUE),
                "Error attribute value not obtained in " + def + ".");

        String key = StringUtils.requireNonNull(keyAttribute.getValue(),
                "Error value cannot empty.", XMLParseException.class);
        String value = StringUtils.requireNonNull(valueAttribute.getValue(),
                "Error value cnannot empty.", XMLParseException.class);
        urlParam.put(key, value);
      }
    }
  }

  /**
   * 将url参数组装到url的字符串中
   */
  void buildURL() {
    for (Map.Entry<String, String> param : urlParam.entrySet()) {
      String key = param.getKey();
      String value = param.getValue();
      String paramValue = key.concat("=").concat(value);
      if (!"?".equals(StringUtils.getLast(url))) {
        url = url.concat("?");
      } else {
        if (!"&".equals(StringUtils.getLast(url))) {
          url = url.concat("&");
        }
      }
      url = url.concat(paramValue);
    }
  }

  /**
   * 检查节点是否未定义
   *
   * @param element 元素对象
   * @param name    节点名称
   */
  void checkUndefine(Element element, String name) {
    if (element == null) {
      throw new NullPointerException("Error " + name + " node undeine.");
    }
  }

  /**
   * 设置数据源节点属性值
   */
  void setting(Element element) {
    // 获取数据库类型
    Attribute typeAttribute = element.getAttribute(TYPE);
    //
    // 如果没有设置类型的话就自动匹配类型，如果自动匹配类型还是匹配不到的话那么就使用默认的JDBC
    //
    String typeKey = null;
    if (typeAttribute != null) {
      typeKey = typeAttribute.getValue();
      if (StringUtils.isEmpty(typeKey)) {
        type = getDataBaseSupport(driver);
      }
    }
    type = getDataBaseSupport(typeKey);
    // 获取数据源对应的任务
    String taskAttributeValue = null;
    Attribute taskAttribute = element.getAttribute(TASK);
    if (taskAttribute != null) {
      taskAttributeValue = taskAttribute.getValue();
      if (StringUtils.isEmpty(taskAttributeValue)) {
        task = DataSourceTask.ALL;
      }
    }
    task = getDataSourceTask(taskAttributeValue);
  }

  DatabaseSupport getDataBaseSupport(String key) {
    key = StringUtils.toLowerCase(key);
    if (key.contains("mysql")) {
      return DatabaseSupport.MYSQL;
    } else if (key.contains("oracle")) {
      return DatabaseSupport.ORACLE;
    } else {
      return DatabaseSupport.JDBC;
    }
  }

  DataSourceTask getDataSourceTask(String key) {
    key = StringUtils.toLowerCase(key);
    if (key.contains("read")) {
      return DataSourceTask.READ;
    } else if (key.contains("write")) {
      return DataSourceTask.WRITE;
    } else {
      return DataSourceTask.ALL;
    }
  }

}
