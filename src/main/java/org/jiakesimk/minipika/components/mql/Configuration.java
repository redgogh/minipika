package org.jiakesimk.minipika.components.mql;

/*
 * Creates on 2019/11/13.
 */

import org.jiakesimk.minipika.components.annotation.QueryMode;
import org.jiakesimk.minipika.components.jdbc.SQLExecutor;
import org.jiakesimk.minipika.framework.annotations.Component;

import java.lang.reflect.Method;

/**
 * 执行方法配置类
 * @author lts
 */
public class Configuration {

  /** sql执行器 **/
  @Component
  private SQLExecutor executor;

  /** 实例对象 **/
  private Object instance;

  /** 方法对象 **/
  private Method method;

  /** 返回类型 **/
  private Class<?> returnType;

  /** 查询模式，是查询List还是Map或者是其他 **/
  private QueryMode mode;

  /** 调用指定查询方法 **/
  private Method queryMethod;

}
