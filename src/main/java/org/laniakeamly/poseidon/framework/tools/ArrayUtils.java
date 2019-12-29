package org.laniakeamly.poseidon.framework.tools;

import java.util.Arrays;

/**
 * 数组工具类
 * Create by 2BKeyboard on 2019/12/13 18:36
 */
public final class ArrayUtils {

    /**
     * 是否为数组
     * @param o
     * @return
     */
    public static boolean isArray(Object o) {
        if (o == null) return false;
        return o.getClass().isArray();
    }

    /**
     * 数组插入
     * @param pos
     * @param data
     * @param dest
     * @return
     */
    public static int[] insert(int pos, int data, int[] dest) {
        pos = pos + 1;
        dest = Arrays.copyOf(dest, dest.length + 1);
        int[] begin = Arrays.copyOfRange(dest, 0, pos);
        begin = Arrays.copyOf(begin, begin.length + 1);
        int[] end = Arrays.copyOfRange(dest, pos, dest.length - 1);
        begin[(pos)] = data;
        System.arraycopy(begin, 0, dest, 0, begin.length);
        System.arraycopy(end, 0, dest, pos + 1, end.length);
        return dest;
    }

}
