package org.jiakesimk.minipika.framework.utils;

/* ************************************************************************
 *
 * Copyright (C) 2020 2B键盘 All rights reserved.
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
 * Creates on 2020/3/21.
 */

import groovyjarjarpicocli.CommandLine;
import org.jiakesimk.minipika.framework.exception.MinipikaException;

import java.lang.reflect.Method;
import java.util.*;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

/**
 * 静态的List工具类
 *
 * @author lts
 * @email jiakesiws@gmail.com
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
    return new ArrayList<>(ofList(es));
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
    return new LinkedList<>(ofList(es));
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
   * 复制一个{@link Collection}中的数据到新的{@code Vector}中
   *
   * @return 新的{@code Vector}且带有传入{@code Collection}数据的实例
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
    return new Vector<>(ofList(es));
  }

  /**
   * 创建一个线程安全的List{@link CopyOnWriteArrayList}
   *
   * @return 空的{@code CopyOnWriteArrayList}
   */
  public static <E> List<E> newCopyOnWriteArrayList() {
    return new CopyOnWriteArrayList<>();
  }

  /**
   * 创建一个可变的 {@code CopyOnWriteArrayList}
   * 复制一个{@link Collection}中的数据到新的{@code CopyOnWriteArrayList}中
   *
   * @return 新的{@code CopyOnWriteArrayList}且带有传入{@code CopyOnWriteArrayList}数据的实例
   */
  public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayList(Collection<? extends E> collection) {
    return new CopyOnWriteArrayList<>(collection);
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
   * // 假设需求是需要对年龄进行降序，可以这样使用
   * List<LocalUser> users = Lists.newArrayList();
   * // 省略查询users代码...
   * users.sort((e1,e2) -> e2.getAge().compareTo(e1.getAge()));
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

  /**
   * 获取方法返回List集合泛型中的返回值
   *
   * @param method 方法对象
   * @return 泛型类型的类名全称
   */
  public static String getGenericType(Method method) {
    if(method.getReturnType() == List.class){
      int listGenericLength = "java.util.List<".length();
      String generic = Methods.getGenericReturnType(method);
      return generic.substring(listGenericLength, generic.length() - 1);
    }
    throw new MinipikaException("Error get generic type failure of java.util.List. " +
            "Cause: the return type of method not java.util.List.");
  }

  /**
   * 数组转List
   *
   * @param a 需要转化的数组
   * @return List集合对象
   */
  @SafeVarargs
  public static <E> List<E> ofList(E... a) {
    return new CopyArrayList<E>(a);
  }

  /**
   * 自定义数组
   */
  private static class CopyArrayList<E> extends AbstractList<E>
          implements RandomAccess, java.io.Serializable {

    private final E[] a;

    CopyArrayList(E[] array) {
      a = java.util.Objects.requireNonNull(array);
    }

    @Override
    public int size() {
      return a.length;
    }

    @Override
    public Object[] toArray() {
      return a.clone();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
      int size = size();
      if (a.length < size)
        return java.util.Arrays.copyOf(this.a, size,
                (Class<? extends T[]>) a.getClass());
      System.arraycopy(this.a, 0, a, 0, size);
      if (a.length > size)
        a[size] = null;
      return a;
    }

    @Override
    public E get(int index) {
      return a[index];
    }

    @Override
    public E set(int index, E element) {
      E oldValue = a[index];
      a[index] = element;
      return oldValue;
    }

    @Override
    public int indexOf(Object o) {
      E[] a = this.a;
      if (o == null) {
        for (int i = 0; i < a.length; i++)
          if (a[i] == null)
            return i;
      } else {
        for (int i = 0; i < a.length; i++)
          if (o.equals(a[i]))
            return i;
      }
      return -1;
    }

    @Override
    public boolean contains(Object o) {
      return indexOf(o) != -1;
    }

    @Override
    public Spliterator<E> spliterator() {
      return Spliterators.spliterator(a, Spliterator.ORDERED);
    }

    @Override
    public void forEach(Consumer<? super E> action) {
      java.util.Objects.requireNonNull(action);
      for (E e : a) {
        action.accept(e);
      }
    }

    @Override
    public void replaceAll(UnaryOperator<E> operator) {
      Objects.requireNonNull(operator);
      E[] a = this.a;
      for (int i = 0; i < a.length; i++) {
        a[i] = operator.apply(a[i]);
      }
    }

    @Override
    public void sort(Comparator<? super E> c) {
      java.util.Arrays.sort(a, c);
    }

  }

  /**
   * List转String
   *
   * @param list list集合
   * @return 转换后的String对象
   */
  public static String toString(List<?> list) {
    StringBuilder str = new StringBuilder("[");
    for (Object o : list) {
      str.append(o).append(",");
    }
    str = new StringBuilder(str.substring(0, str.length() - 1) + "]");
    return str.toString();
  }

}
