package org.jiakesimk.minipika.components.wrapper

interface QueryCompare {

  /**
   * 等于
   *
   * @param column 字段名
   * @param object 比较值
   * @return 当前比较器（this）
   */
  QueryCompare eq(column, object)

  /**
   * 不等于
   *
   * @param column 字段名
   * @param object 比较值
   * @return 当前比较器（this）
   */
  QueryCompare ne(column, object)

  /**
   * 等于空
   *
   * @param column 字段名
   * @return 当前比较器（this）
   */
  QueryCompare eqNull(column)

  /**
   * 不等于空
   *
   * @param column 字段名
   * @return 当前比较器（this）
   */
  QueryCompare neNull(column)

  /**
   * 大于
   *
   * @param column 字段名
   * @param object 比较值
   * @return 当前比较器（this）
   */
  QueryCompare gt(column, object)

  /**
   * 大于等于
   *
   * @param column 字段名
   * @param object 比较值
   * @return 当前比较器（this）
   */
  QueryCompare ge(column, object)

  /**
   * 小于等于
   *
   * @param column 字段名
   * @param object 比较值
   * @return 当前比较器（this）
   */
  QueryCompare le(column, object)

  /**
   * 小于
   *
   * @param column 字段名
   * @param object 比较值
   * @return 当前比较器（this）
   */
  QueryCompare lt(column, object)

  /**
   * 模糊，可以使用 %字段 | 字段% | %字段%
   *
   * @param column 字段名
   * @param object 比较值
   * @return 当前比较器（this）
   */
  QueryCompare like(column, object)

  /**
   * 当需要查询的数据在多少天前的时候使用此方法
   *
   * @param column 字段名
   * @param day 比较值
   * @return 当前比较器（this）
   */
  QueryCompare da(column, int day)

  /**
   * 当需要查询的数据在多少天后的时候使用此方法
   *
   * @param column 字段名
   * @param day 比较值
   * @return 当前比较器（this）
   */
  QueryCompare dl(column, int day)

  /**
   * 当需要查询字段在a-b之间的时候使用此方法，
   * a-b不限制字段类型
   *
   * @param column 字段名
   * @param a a值
   * @param b b值
   * @return 当前比较器（this）
   */
  QueryCompare between(column, a, b)

}