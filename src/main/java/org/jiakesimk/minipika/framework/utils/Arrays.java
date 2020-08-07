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
 * Creates on 2019/12/13
 */

import java.lang.reflect.Array;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * 静态数组工具类
 *
 * @author lts
 * @email jiakesiws@gmail.com
 */
@SuppressWarnings({"rawtypes"})
public final class Arrays {

  public enum OP {FIRST, LAST}

  /**
   * 判断对象是否为数组
   */
  public static boolean isArray(Object o) {
    return o != null && o.getClass().isArray();
  }

  /**
   * 插入byte数组
   *
   * @param pos  开始下标
   * @param data 需要插入的数据
   * @param dest 目标数组
   * @return 新的byte数组
   */
  public static byte[] insert(final byte[] dest, int pos, byte... data) {
    byte[] destCopy = new byte[dest.length + data.length];
    System.arraycopy(dest, 0, destCopy, 0, pos);
    System.arraycopy(data, 0, destCopy, pos, data.length);
    System.arraycopy(dest, pos, destCopy, data.length + pos, dest.length - pos);
    return destCopy;
  }

  /**
   * 插入short数组
   *
   * @param pos  开始下标
   * @param data 需要插入的数据
   * @param dest 目标数组
   * @return 新的short数组
   */
  public static short[] insert(final short[] dest, int pos, short... data) {
    short[] destCopy = new short[dest.length + data.length];
    System.arraycopy(dest, 0, destCopy, 0, pos);
    System.arraycopy(data, 0, destCopy, pos, data.length);
    System.arraycopy(dest, pos, destCopy, data.length + pos, dest.length - pos);
    return destCopy;
  }


  /**
   * 插入int数组
   *
   * @param pos  开始下标
   * @param data 需要插入的数据
   * @param dest 目标数组
   * @return 新的int数组
   */
  public static int[] insert(final int[] dest, int pos, int... data) {
    int[] destCopy = new int[dest.length + data.length];
    System.arraycopy(dest, 0, destCopy, 0, pos);
    System.arraycopy(data, 0, destCopy, pos, data.length);
    System.arraycopy(dest, pos, destCopy, data.length + pos, dest.length - pos);
    return destCopy;
  }

  /**
   * 插入long数组
   *
   * @param pos  开始下标
   * @param data 需要插入的数据
   * @param dest 目标数组
   * @return 新的long数组
   */
  public static long[] insert(final long[] dest, int pos, long... data) {
    long[] destCopy = new long[dest.length + data.length];
    System.arraycopy(dest, 0, destCopy, 0, pos);
    System.arraycopy(data, 0, destCopy, pos, data.length);
    System.arraycopy(dest, pos, destCopy, data.length + pos, dest.length - pos);
    return destCopy;
  }

  /**
   * 插入float数组
   *
   * @param pos  开始下标
   * @param data 需要插入的数据
   * @param dest 目标数组
   * @return 新的float数组
   */
  public static float[] insert(final float[] dest, int pos, float... data) {
    float[] destCopy = new float[dest.length + data.length];
    System.arraycopy(dest, 0, destCopy, 0, pos);
    System.arraycopy(data, 0, destCopy, pos, data.length);
    System.arraycopy(dest, pos, destCopy, data.length + pos, dest.length - pos);
    return destCopy;
  }

  /**
   * 插入double数组
   *
   * @param pos  开始下标
   * @param data 需要插入的数据
   * @param dest 目标数组
   * @return 新的double数组
   */
  public static double[] insert(final double[] dest, int pos, double... data) {
    double[] destCopy = new double[dest.length + data.length];
    System.arraycopy(dest, 0, destCopy, 0, pos);
    System.arraycopy(data, 0, destCopy, pos, data.length);
    System.arraycopy(dest, pos, destCopy, data.length + pos, dest.length - pos);
    return destCopy;
  }

  /**
   * 插入boolean数组
   *
   * @param pos  开始下标
   * @param data 需要插入的数据
   * @param dest 目标数组
   * @return 新的boolean数组
   */
  public static boolean[] insert(final boolean[] dest, int pos, boolean... data) {
    boolean[] destCopy = new boolean[dest.length + data.length];
    System.arraycopy(dest, 0, destCopy, 0, pos);
    System.arraycopy(data, 0, destCopy, pos, data.length);
    System.arraycopy(dest, pos, destCopy, data.length + pos, dest.length - pos);
    return destCopy;
  }

  /**
   * 插入char数组
   *
   * @param pos  开始下标
   * @param data 需要插入的数据
   * @param dest 目标数组
   * @return 新的char数组
   */
  public static char[] insert(final char[] dest, int pos, char... data) {
    char[] destCopy = new char[dest.length + data.length];
    System.arraycopy(dest, 0, destCopy, 0, pos);
    System.arraycopy(data, 0, destCopy, pos, data.length);
    System.arraycopy(dest, pos, destCopy, data.length + pos, dest.length - pos);
    return destCopy;
  }

  /**
   * 根据下标删除数组元素
   *
   * @param array 需要删除的数组
   * @param index 需要删除的下标
   */
  public static <T> Object[] remove(T[] array, int index) {
    try {
      ArrayList<T> list = new ArrayList<>(java.util.Arrays.asList(array));
      String typeName = array.getClass().getTypeName();
      typeName = typeName.substring(0, typeName.length() - 2); // 去除 '[]'
      list.remove(index);
      Class clazz = Class.forName(typeName);
      Object arrayObject = Array.newInstance(clazz, array.length - 1);
      return list.toArray((Object[]) arrayObject);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 根据OP枚举来删除
   */
  public static <T> Object[] remove(T[] array, OP op) {
    if (op == OP.FIRST) {
      return remove(array, 0);
    } else if (op == OP.LAST) {
      return remove(array, array.length - 1);
    }
    throw new InvalidParameterException("invalid parameter for op: " + op);
  }

  /**
   * append data for array.
   *
   * @param array 需要追加的数组 | the array
   * @param value 需要追加的数据 | append value
   * @return false代表数组已经满了不能追加 | false express array full
   */
  public static <T> boolean append(T[] array, T value) {
    for (int i = 0; i < array.length; i++) {
      if (array[i] == null) {
        array[i] = value;
        return true;
      }
    }
    return false;
  }

  /**
   * 根据下标添加
   *
   * @param array 需要追加的数组 | the array
   * @param value 需要追加的数据 | append value
   * @param index 在哪个位置追加数据 | append data by index
   * @return false代表数组已经满了不能追加 | false express array full
   */
  public static <T> boolean append(T[] array, T value, int index) {
    if (array.length < index)
      return false;
    array[index] = value;
    return false;
  }

  /**
   * 复制数组原始长度，并将数组扩容到capacity长度，使用null填充。
   *
   * @param a        数组对象
   * @param capacity 容量
   * @return 扩容后的数组
   */
  public static <T> T[] copyOf(T[] a, int capacity) {
    return java.util.Arrays.copyOf(a, capacity);
  }

  /**
   * 数组转List，调用{@link Lists#ofList}方法转数组
   *
   * @param a 数组对象
   * @return 实例化的List对象
   */
  public static <E> List<E> asList(E[] a) {
    return Lists.ofList(a);
  }

  /**
   * 对象转数组, 传入的对象必须是数组转成的。
   */
  public static Object[] toArray(Object a) {
    Object[] objects = null;
    if(a != null) {
      if (isArray(a)) {
        int size = Array.getLength(a);
        objects = new Object[size];
        for (int i = 0; i < size; i++) {
          objects[i] = Array.get(a, i);
        }
        return objects;
      } else if (a instanceof List) {
        List<?> list = (List<?>) a;
        int len = list.size();
        objects = new Object[len];
        for (int i = 0; i < len; i++) {
          objects[i] = list.get(i);
        }
      }
    }
    return objects;
  }

  /**
   * 数组的{@code toString}方法
   */
  public static String toString(Object array) {
    if (!isArray(array)) {
      throw new Error("parameter object not array.");
    }
    Object[] array0 = toArray(array);
    String str = "[";
    for (Object o : array0) {
      str += o + ",";
    }
    str = str.substring(0, str.length() - 1) + "]";
    return str;
  }

  /**
   * 数组的{@code toString}方法
   */
  public static String toString(Object array, String delimiter) {
    if (!isArray(array)) {
      throw new Error("parameter object not array.");
    }
    Object[] array0 = toArray(array);
    StringBuilder str = new StringBuilder("[");
    for (Object o : array0) {
      str.append(o).append(delimiter);
    }
    str = new StringBuilder(str.substring(0, str.length() - 1) + "]");
    return str.toString();
  }

}
