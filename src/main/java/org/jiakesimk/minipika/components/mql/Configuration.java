package org.jiakesimk.minipika.components.mql;

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
 * Creates on 2019/11/13.
 */

import org.jiakesimk.minipika.components.annotation.*;
import org.jiakesimk.minipika.components.jdbc.Executor;
import org.jiakesimk.minipika.framework.annotations.Component;
import org.jiakesimk.minipika.framework.common.ConstVariable;
import org.jiakesimk.minipika.framework.utils.Lists;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 执行方法配置类
 *
 * @author tiansheng
 * @email jiakesiws@gmail.com
 */
public class Configuration {

  /**
   * 配置Id标识，用于快速查找配置信息
   */
  private String configurationId;

  /**
   * sql执行器
   **/
  @Component
  private Executor executor;

  /**
   * 实例对象
   **/
  private Object instance;

  /**
   * 方法对象
   **/
  private Method method;

  /**
   * 返回类型
   **/
  private Class<?> returnType;

  /**
   * 查询模式，是查询List还是Map或者是其他
   **/
  private QueryMode queryMode;

  /**
   * 调用指定查询方法
   **/
  private Method executeMethod;

  /**
   * 唯一有参构造器
   *
   * @param method 调用方法
   * @see IFaceAgent#invoke(Object, Method, Object[])
   */
  public Configuration(Method method, Object instance) throws NoSuchMethodException, ClassNotFoundException {
    this.method = method;
    this.instance = instance;
    initMemberProperty(method);
  }

  /**
   * 执行查询
   *
   * @param args 参数列表
   * @param <T>  泛型
   * @return 查询结果
   * @throws InvocationTargetException 执行异常
   * @throws IllegalAccessException    非法访问异常
   */
  @SuppressWarnings("unchecked")
  public <T> T execute(String sql, Object[] args) throws InvocationTargetException, IllegalAccessException {
    if(returnType != null) {
      return (T) executeMethod.invoke(executor, sql, returnType, args);
    } else {
      return (T) executeMethod.invoke(executor, sql, args);
    }
  }

  /**
   * 初始化成员属性
   *
   * @param method mapper中定义的方法
   * @throws NoSuchMethodException 反射找不到方法异常
   */
  public void initMemberProperty(Method method) throws NoSuchMethodException, ClassNotFoundException {
    if (method.isAnnotationPresent(ConstVariable.A_QUERY_OF)) {
      Class<?> returnType = method.getReturnType();
      if (returnType == List.class) {
        this.queryMode = QueryMode.FOR_LIST;
        returnType = Class.forName(Lists.getGenericType(method));
        this.executeMethod = getSQLExecuteMethod("queryForList", String.class, Class.class, Object[].class);
      } else {
        this.queryMode = QueryMode.FOR_OBJECT; // 默认查询单个对象
        this.executeMethod = getSQLExecuteMethod("queryForObject", String.class, Class.class, Object[].class);
      }
      this.returnType = returnType;
    } else if (method.isAnnotationPresent(ConstVariable.A_UPDATE)) {
      this.executeMethod = getSQLExecuteMethod("update", String.class, Object[].class);
    } else if (method.isAnnotationPresent(ConstVariable.A_INSERT)) {
      this.executeMethod = getSQLExecuteMethod("insert", String.class, Object[].class);
    } else if (method.isAnnotationPresent(ConstVariable.A_BATCH)) {
      this.executeMethod = getSQLExecuteMethod("batch", String.class, Object[].class);
    }
  }

  /**
   * 获取执行器方法对象
   *
   * @param name           方法名
   * @param parameterTypes 参数列表
   * @return 对应的方法对象
   * @throws NoSuchMethodException 找不到方法异常
   */
  private Method getSQLExecuteMethod(String name, Class<?>... parameterTypes) throws NoSuchMethodException {
    Class<?> executeClass = Executor.class;
    return executeClass.getDeclaredMethod(name, parameterTypes);
  }

  public String getConfigurationId() {
    return configurationId;
  }

  public void setConfigurationId(String configurationId) {
    this.configurationId = configurationId;
  }

  public Executor getExecutor() {
    return executor;
  }

  public void setExecutor(Executor executor) {
    this.executor = executor;
  }

  public Object getInstance() {
    return instance;
  }

  public void setInstance(Object instance) {
    this.instance = instance;
  }

  public Method getMethod() {
    return method;
  }

  public void setMethod(Method method) {
    this.method = method;
  }

  public Class<?> getReturnType() {
    return returnType;
  }

  public void setReturnType(Class<?> returnType) {
    this.returnType = returnType;
  }

  public QueryMode getQueryMode() {
    return queryMode;
  }

  public void setQueryMode(QueryMode queryMode) {
    this.queryMode = queryMode;
  }

  public Method getExecuteMethod() {
    return executeMethod;
  }

  public void setExecuteMethod(Method executeMethod) {
    this.executeMethod = executeMethod;
  }

}
