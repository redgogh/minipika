package org.laniakeamly.poseidon.framework.tools;

import java.lang.Object;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * 数组工具类
 * Create by 2BKeyboard on 2019/12/13 18:36
 */
public final class ArrayUtils {

    /**
     * 对象是否为数组
     * whether the object is array type.
     *
     * @param o
     * @return
     */
    public static boolean isArray(Object o) {
        if (o == null) return false;
        return o.getClass().isArray();
    }

    /**
     * 插入byte数组
     * insert byte type array.
     *
     * @param pos       开始下标
     *                  start index
     *
     * @param data      需要插入的数据
     *                  insert the data.
     *
     * @param dest      目标数组
     *                  target array.
     * @return
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
     * insert short type array.
     *
     * @param pos       开始下标
     *                  start index
     *
     * @param data      需要插入的数据
     *                  insert the data.
     *
     * @param dest      目标数组
     *                  target array.
     * @return
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
     * insert int type array.
     *
     * @param pos       开始下标
     *                  start index
     *
     * @param data      需要插入的数据
     *                  insert the data.
     *
     * @param dest      目标数组
     *                  target array.
     * @return
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
     * insert long type array.
     *
     * @param pos       开始下标
     *                  start index
     *
     * @param data      需要插入的数据
     *                  insert the data.
     *
     * @param dest      目标数组
     *                  target array.
     * @return
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
     * insert float type array.
     *
     * @param pos       开始下标
     *                  start index
     *
     * @param data      需要插入的数据
     *                  insert the data.
     *
     * @param dest      目标数组
     *                  target array.
     * @return
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
     * insert double type array.
     *
     * @param pos       开始下标
     *                  start index
     *
     * @param data      需要插入的数据
     *                  insert the data.
     *
     * @param dest      目标数组
     *                  target array.
     * @return
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
     * insert boolean type array.
     *
     * @param pos       开始下标
     *                  start index
     *
     * @param data      需要插入的数据
     *                  insert the data.
     *
     * @param dest      目标数组
     *                  target array.
     * @return
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
     * insert char type array.
     *
     * @param pos       开始下标
     *                  start index
     *
     * @param data      需要插入的数据
     *                  insert the data.
     *
     * @param dest      目标数组
     *                  target array.
     * @return
     */
    public static char[] insert(final char[] dest, int pos, char... data) {
        char[] destCopy = new char[dest.length + data.length];
        System.arraycopy(dest, 0, destCopy, 0, pos);
        System.arraycopy(data, 0, destCopy, pos, data.length);
        System.arraycopy(dest, pos, destCopy, data.length + pos, dest.length - pos);
        return destCopy;
    }

    public static void main(String[] args) {
        System.out.println();
    }

}
