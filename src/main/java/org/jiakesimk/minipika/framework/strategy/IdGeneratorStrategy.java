package org.jiakesimk.minipika.framework.strategy;

/*
 * Creates on 2020/7/15.
 */

/**
 * id生成策略
 *
 * @author lts
 */
public interface IdGeneratorStrategy {

  /**
   * @return 自定义的ID生成策略生成出来的ID
   */
  Object customIdGenerator();

}