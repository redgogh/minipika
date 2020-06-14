package org.jiakesiws.minipika.framework.util;

import java.util.*;

/**
 * 静态的List工具类
 *
 * @author lts
 * @since 1.8
 */
public final class Lists {

  private Lists() {
  }

  /**
   * 升序，从大到小
   */
  public static final int ASC = 0;

  /**
   * 降序，从大到小
   */
  public static final int DESC = 1;

  /**
   * 创建一个可变的 {@code ArrayList}
   *
   * @return 空的 {@code ArrayList} 实例
   */
  public static <E> ArrayList<E> newArrayList() {
    return new ArrayList<>();
  }

  /**
   * 创建一个可变的 {@code ArrayList} 并初始化容量
   *
   * @return 空的 {@code ArrayList} 实例
   */
  public static <E> ArrayList<E> newArrayList(int capacity) {
    return new ArrayList<>(capacity);
  }

  /**
   * 创建一个可变的 {@code ArrayList}
   * 复制一个{@link List}中的数据到新的{@code List}中
   *
   * @return 新的List且带有传入List数据的实例
   */
  public static <E> ArrayList<E> newArrayList(Collection<? extends E> collection) {
    return new ArrayList<>(collection);
  }

  /**
   * 创建一个可变的 {@code ArrayList}
   * 复制数组中的内容到新创建的{@code ArrayList}中
   *
   * @return 新的List且带有传入List数据的实例
   */
  public static <E> ArrayList<E> newArrayList(E[] es) {
    return new ArrayList<>(Arrays.asList(es));
  }

  /**
   * 创建一个可变的 {@code LinkedList}
   *
   * @return 空的 {@code LinkedList} 实例
   */
  public static <E> LinkedList<E> newLinkedList() {
    return new LinkedList<>();
  }

  /**
   * 创建一个可变的 {@code LinkedList}
   * 复制一个{@link List}中的数据到新的{@code LinkedList}中
   *
   * @return 新的List且带有传入List数据的实例
   */
  public static <E> LinkedList<E> newLinkedList(Collection<? extends E> collection) {
    return new LinkedList<>(collection);
  }

  /**
   * 创建一个可变的 {@code LinkedList}
   * 复制数组中的内容到新创建的{@code LinkedList}中
   *
   * @return 新的{@code List}且带有传入数组的数据的实例
   */
  public static <E> LinkedList<E> newLinkedList(E[] es) {
    return new LinkedList<>(Arrays.asList(es));
  }

  /**
   * 创建一个可变的 {@code Vector}
   *
   * @return 空的 {@code Vector} 实例.
   */
  public static <E> Vector<E> newVector() {
    return new Vector<>();
  }

  /**
   * 创建一个可变的 {@code Vector} 并初始化容量
   *
   * @return 空的 {@code Vector} 实例.
   */
  public static <E> Vector<E> newVector(int capacity) {
    return new Vector<>(capacity);
  }

  /**
   * 创建一个可变的 {@code Vector}
   * 复制一个{@link Vector}中的数据到新的{@code Vector}中
   *
   * @return 新的{@code Vector}且带有传入{@code Vector}数据的实例
   */
  public static <E> Vector<E> newVector(Collection<? extends E> collection) {
    return new Vector<>(collection);
  }

  /**
   * 创建一个可变的 {@code Vector}
   * 复制一个数组中的数据到新的{@code Vector}中
   *
   * @return 新的{@code Vector}且带有传入的数组数据的实例
   */
  public static <E> Vector<E> newVector(E[] es) {
    return new Vector<>(Arrays.asList(es));
  }

  /**
   * 对{@code List}进行简单的排序
   *
   * @param collection 集合实例
   * @param type       根据哪种类型进行排序，有以下几种
   *                   {@link Lists#ASC} ASC为升序
   * @link DESC} DESC则为降序
   * <p>
   * 如果List中的元素是对象的话，如果要根据对象的某个值进行排序的话请实现{@link Comparable}接口。例如：<code>
   *   // 假设需求是需要对年龄进行降序，可以这样使用
   *   List<LocalUser> users = Lists.newArrayList();
   *   // 省略查询users代码...
   *   users.sort((e1,e2) -> e2.getAge().compareTo(e1.getAge()));
   * </code>
   */
  public static <T extends Comparable<T>> void sort(List<T> collection, int type) {
    switch (type) {
      case ASC:
        Collections.sort(collection);
        break;
      case DESC:
        Collections.reverse(collection);
        break;
      default:
    }
  }

  public static <T> List<T> asList(T[] objects) {
    if(!ArrayUtils.isArray(objects)) return null;
    return Arrays.asList(objects);
  }

}
