package org.jiakesiws.minipika.framework.util;

/*
 * Creates on 2019/12/13
 */

import java.lang.reflect.Array;
import java.security.InvalidParameterException;
import java.util.ArrayList;

/**
 * 静态数组工具类
 *
 * @author lts
 */
@SuppressWarnings({"rawtypes"})
public final class ArrayUtils {

  public enum OP {FIRST, LAST}

  /**
   * 判断对象是否为数组
   */
  public static boolean isArray(Object o) {
    if (o == null) return false;
    Class c = o.getClass();
    return c.isArray() || ((Class) o).getName().contains("[");
  }

  /**
   * 插入byte数组
   * insert byte type array.
   *
   * @param pos  开始下标
   *             start index
   * @param data 需要插入的数据
   *             insert the data.
   * @param dest 目标数组
   *             target array.
   * @return
   */
  public static byte[] insert(final byte[] dest, int pos, byte... data) {
    byte[] destCopy = new byte[dest.length + data.length];
    System.arraycopy(dest, 0, destCopy, 0, pos);
    System.arraycopy(data, 0, destCopy, pos, data.length);
    System.arraycopy(dest, pos, destCopy, data.length + pos, dest.length - pos);
    return destCopy;
  }

  public static short[] insert(final short[] dest, int pos, short... data) {
    short[] destCopy = new short[dest.length + data.length];
    System.arraycopy(dest, 0, destCopy, 0, pos);
    System.arraycopy(data, 0, destCopy, pos, data.length);
    System.arraycopy(dest, pos, destCopy, data.length + pos, dest.length - pos);
    return destCopy;
  }


  public static int[] insert(final int[] dest, int pos, int... data) {
    int[] destCopy = new int[dest.length + data.length];
    System.arraycopy(dest, 0, destCopy, 0, pos);
    System.arraycopy(data, 0, destCopy, pos, data.length);
    System.arraycopy(dest, pos, destCopy, data.length + pos, dest.length - pos);
    return destCopy;
  }

  public static long[] insert(final long[] dest, int pos, long... data) {
    long[] destCopy = new long[dest.length + data.length];
    System.arraycopy(dest, 0, destCopy, 0, pos);
    System.arraycopy(data, 0, destCopy, pos, data.length);
    System.arraycopy(dest, pos, destCopy, data.length + pos, dest.length - pos);
    return destCopy;
  }

  public static float[] insert(final float[] dest, int pos, float... data) {
    float[] destCopy = new float[dest.length + data.length];
    System.arraycopy(dest, 0, destCopy, 0, pos);
    System.arraycopy(data, 0, destCopy, pos, data.length);
    System.arraycopy(dest, pos, destCopy, data.length + pos, dest.length - pos);
    return destCopy;
  }

  public static double[] insert(final double[] dest, int pos, double... data) {
    double[] destCopy = new double[dest.length + data.length];
    System.arraycopy(dest, 0, destCopy, 0, pos);
    System.arraycopy(data, 0, destCopy, pos, data.length);
    System.arraycopy(dest, pos, destCopy, data.length + pos, dest.length - pos);
    return destCopy;
  }

  public static boolean[] insert(final boolean[] dest, int pos, boolean... data) {
    boolean[] destCopy = new boolean[dest.length + data.length];
    System.arraycopy(dest, 0, destCopy, 0, pos);
    System.arraycopy(data, 0, destCopy, pos, data.length);
    System.arraycopy(dest, pos, destCopy, data.length + pos, dest.length - pos);
    return destCopy;
  }

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
   * 数组扩容
   */
  public static Object[] expansion(Object[] array, int size, Class<?> type) {
    Object[] dest = (Object[]) Array.newInstance(type, (array.length + size));
    System.arraycopy(array, 0, dest, 0, array.length);
    return dest;
  }

  /**
   * 对象转数组, 传入的对象必须是数组转成的。
   */
  public static Object[] toArray(Object array) {
    if (!isArray(array)) return null;
    int size = Array.getLength(array);
    Object[] objects = new Object[size];
    for (int i = 0; i < size; i++) {
      objects[i] = Array.get(array, i);
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
    String str = "[";
    for (Object o : array0) {
      str += o + delimiter;
    }
    str = str.substring(0, str.length() - 1) + "]";
    return str;
  }

}
