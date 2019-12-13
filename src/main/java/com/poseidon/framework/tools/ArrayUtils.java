package com.poseidon.framework.tools;

import java.util.Arrays;

/**
 * 数组工具类
 * Create by 2BKeyboard on 2019/12/13 18:36
 */
public class ArrayUtils {

    public static int[] insert(int pos,int data,int[] dest){
        pos = pos +1;
        dest = Arrays.copyOf(dest,dest.length+1);
        int[] begin = Arrays.copyOfRange(dest,0,pos);
        begin = Arrays.copyOf(begin,begin.length+1);
        int[] end =Arrays.copyOfRange(dest,pos,dest.length-1);
        begin[(pos)] = data;
        System.arraycopy(begin,0,dest,0,begin.length);
        System.arraycopy(end,0,dest,pos+1,end.length);
        return dest;
    }

}
