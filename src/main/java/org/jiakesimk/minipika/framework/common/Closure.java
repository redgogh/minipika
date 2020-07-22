package org.jiakesimk.minipika.framework.common;

/*
 * Creates on 2019/11/13.
 */

/**
 * @author lts
 * @email ltsloveyellow@aliyun.com
 */
public interface Closure<T> {
  T accept(T o);
}
