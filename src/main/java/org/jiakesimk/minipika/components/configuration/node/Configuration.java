package org.jiakesimk.minipika.components.configuration.node;

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

import org.jdom2.Attribute;
import org.jiakesimk.minipika.components.configuration.wrapper.ElementWrapper;
import org.jiakesimk.minipika.components.jdbc.datasource.DataSourceManager;
import org.jiakesimk.minipika.components.jdbc.datasource.pooled.PooledDataSource;
import org.jiakesimk.minipika.components.jdbc.datasource.unpooled.UnpooledDataSource;
import org.jiakesimk.minipika.framework.exception.XMLParseException;
import org.jiakesimk.minipika.framework.util.Maps;
import org.jiakesimk.minipika.framework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * 单个数据源节点下的数据, 比如master、slaves
 *
 * @author tiansheng
 */
public class Configuration implements ElementParser {

  private String url;
  private String username;
  private String password;
  private String driver;
  private Map<String, String> urlParam = Maps.newHashMap();

  private DataSourceTask task;
  private Databases type;

  // 数据源名称
  private String name;

  // 是否选择自动提交, 默认false
  private boolean desiredAutoCommit;

  private boolean pooled = true; // 当前数据源是不是池化的数据源

  public static final String URL                = "url";

  public static final String USERNAME           = "username";

  public static final String PASSWORD           = "password";

  public static final String DRIVER             = "driver";

  public static final String VALUE              = "value";

  public static final String TYPE               = "type";

  public static final String TASK               = "task";

  public static final String AUTO_COMMIT        = "commit";

  public static final String NAME               = "name";

  /**
   * 链接属性
   */
  private Properties info;

  /**
   * 解析完后将当前数据源注册到{@link DataSourceManager}
   */
  private void register() {
    UnpooledDataSource dataSource = new UnpooledDataSource(this);
    if (pooled) {
      new PooledDataSource(dataSource);
    }
    if(DataSourceManager.MASTER.equals(name)) {
      DataSourceManager.select(DataSourceNode.MASTER);
    }
  }

  @Override
  public void parse(ElementWrapper element) {
    // 解析URL属性
    ElementWrapper urlElement = element.getChild(URL);
    checkUndefine(urlElement, URL);
    parseURLElementNode(urlElement);

    // 解析username
    ElementWrapper usernameElement = element.getChild(USERNAME);
    Attribute usernameAttribute = usernameElement.getAttribute(VALUE);
    if (usernameAttribute == null) {
      throw new XMLParseException("Error username node of DataSource node undefine.");
    }
    username = StringUtils.requireNonNull(usernameAttribute.getValue(),
            "Error value of username node cannot empty.", XMLParseException.class);

    // 解析password， password可以为NULL
    ElementWrapper passwordElement = element.getChild(PASSWORD);
    checkUndefine(passwordElement, PASSWORD);
    if (passwordElement != null) {
      Attribute passwordAttribute = passwordElement.getAttribute(VALUE);
      if (passwordAttribute != null && StringUtils.isNotEmpty(passwordAttribute.getValue())) {
        password = passwordAttribute.getValue();
      }
    }

    // 解析driver
    ElementWrapper driverElement = element.getChild("driver");
    checkUndefine(driverElement, DRIVER);
    Attribute attribute = driverElement.getAttribute(VALUE);
    driver = attribute.getValue();

    // 拼接URL参数
    buildURL();
    buildConnectProperties();

    // 设置属性
    setting(element);

    // 将当前数据源注册到数据源管理器中
    register();
  }

  /**
   * 构建链接属性并静态化,避免二次创建
   */
  private void buildConnectProperties() {
    Properties properties = new Properties();
    properties.setProperty(USERNAME, username);
    properties.setProperty(PASSWORD, password);
    this.info = properties;
  }

  /**
   * 解析URL节点
   *
   * @param urlElement url节点元素
   */
  private void parseURLElementNode(ElementWrapper urlElement) {
    Attribute urlAttribute = urlElement.getAttribute(VALUE);
    if (urlAttribute == null) {
      String def = XMLParseException.buildTrack(urlElement);
      throw new XMLParseException("Error the url node of DataSource require " +
              "value attribute in node track " + def + ". " + "Cause: value is required.");
    }
    String urlValue = urlAttribute.getValue();
    if (StringUtils.isEmpty(urlValue)) {
      String def = XMLParseException.buildTrack(urlElement);
      throw new XMLParseException("Error the url node of DataSource the value attribute " +
              "value cannot null in node track " + def + ". " + "Cause: value is required.");
    }
    url = urlValue;
    // 判断URL节点下是否存在子节点
    List<ElementWrapper> urlElementParam = urlElement.getChildren("property");
    if (urlElementParam != null && !urlElementParam.isEmpty()) {
      for (ElementWrapper param : urlElementParam) {
        String def = XMLParseException.buildTrack(urlElement);
        Attribute nameAttribute = Objects.requireNonNull(param.getAttribute(NAME),
                "Error attribute key not obtained in " + def + ".");
        Attribute valueAttribute = Objects.requireNonNull(param.getAttribute(VALUE),
                "Error attribute value not obtained in " + def + ".");

        String name = StringUtils.requireNonNull(nameAttribute.getValue(),
                "Error value cannot empty.", XMLParseException.class);
        String value = StringUtils.requireNonNull(valueAttribute.getValue(),
                "Error value cnannot empty.", XMLParseException.class);
        urlParam.put(name, value);
      }
    }
  }

  /**
   * 将url参数组装到url的字符串中
   */
  void buildURL() {
    String splice = "";
    for (Map.Entry<String, String> param : urlParam.entrySet()) {
      String key = param.getKey();
      String value = param.getValue();
      String paramValue = key.concat("=").concat(value);
      if (!splice.contains("?")) {
        splice = splice.concat("?");
      } else {
        if (!"&".equals(StringUtils.getLast(url))) {
          splice = splice.concat("&");
        }
      }
      splice = splice.concat(paramValue);
    }
    url = url.concat(splice);
  }

  /**
   * 检查节点是否未定义
   *
   * @param element 元素对象
   * @param name    节点名称
   */
  void checkUndefine(ElementWrapper element, String name) {
    if (element == null) {
      throw new NullPointerException("Error " + name + " node undeine.");
    }
  }

  /**
   * 设置数据源节点属性值
   */
  void setting(ElementWrapper element) {
    // 获取数据源名称, 必要名称master
    String name = element.getName();
    if (DataSourceNode.MASTER.equals(name)) {
      this.name = name;
    } else {
      try {
        this.name = element.getAttribute("name").getValue();
      }catch (Exception e) {
        if(e instanceof NullPointerException) {
          throw new NullPointerException("Error: can be null except master tag. All other tag must speify name.");
        }
      }
    }
    // 获取数据源是否需要池化
    Attribute pooledAttribute = element.getAttribute("pooled");
    if (pooledAttribute != null) {
      this.pooled = Boolean.parseBoolean(pooledAttribute.getValue());
    }
    // 获取数据库类型
    Attribute typeAttribute = element.getAttribute(TYPE);
    //
    // 如果没有设置类型的话就自动匹配类型，如果自动匹配类型还是匹配不到的话那么就使用默认的JDBC
    //
    String typeKey = null;
    if (typeAttribute != null) {
      typeKey = typeAttribute.getValue();
    }
    if(StringUtils.isEmpty(typeKey)) {
      type = getDataBaseSupport(driver);
    }
    // 获取数据源对应的任务
    String taskAttributeValue = null;
    Attribute taskAttribute = element.getAttribute(TASK);
    if (taskAttribute != null) {
      taskAttributeValue = taskAttribute.getValue();
      if (StringUtils.isEmpty(taskAttributeValue)) {
        task = DataSourceTask.ALL;
      }
      task = getDataSourceTask(taskAttributeValue);
    } else {
      task = DataSourceTask.ALL;
    }
    // 获取数据源是否需要自动提交
    Attribute desiredAutoCommitAttribute = element.getAttribute(AUTO_COMMIT);
    if (desiredAutoCommitAttribute == null) {
      desiredAutoCommit = false;
    } else {
      String value = desiredAutoCommitAttribute.getValue();
      desiredAutoCommit = Boolean.parseBoolean(value);
    }
  }

  /**
   * 获取当前数据源的数据库类型
   *
   * @param key 配置的数据库类型
   */
  Databases getDataBaseSupport(String key) {
    key = StringUtils.toLowerCase(key);
    if (key.contains("mysql")) {
      return Databases.MYSQL;
    } else if (key.contains("oracle")) {
      return Databases.ORACLE;
    } else {
      return Databases.JDBC;
    }
  }

  /**
   * 获取数据源对应的任务, 如果没有填写默认数据源读写都支持
   *
   * @param key 配置的任务类型
   */
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

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getDriver() {
    return driver;
  }

  public void setDriver(String driver) {
    this.driver = driver;
  }

  public Map<String, String> getUrlParam() {
    return urlParam;
  }

  public void setUrlParam(Map<String, String> urlParam) {
    this.urlParam = urlParam;
  }

  public DataSourceTask getTask() {
    return task;
  }

  public void setTask(DataSourceTask task) {
    this.task = task;
  }

  public Databases getType() {
    return type;
  }

  public void setType(Databases type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isDesiredAutoCommit() {
    return desiredAutoCommit;
  }

  public void setDesiredAutoCommit(boolean desiredAutoCommit) {
    this.desiredAutoCommit = desiredAutoCommit;
  }

  public boolean isPooled() {
    return pooled;
  }

  public void setPooled(boolean pooled) {
    this.pooled = pooled;
  }

}
