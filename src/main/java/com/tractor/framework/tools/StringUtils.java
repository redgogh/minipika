package com.tractor.framework.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String工具类
 *
 * @author tiansheng
 * @version 1.0
 * @date 2019/8/23 23:52
 * @since 1.8
 */
public class StringUtils {

    /**
     * 字符串是否为空
     *
     * @param s 目标字符串
     * @return 返回boolean
     */
    public static boolean isEmpty(String s) {
        if (s == null) {
            return true;
        }
        // Unicode 编码下的空格
        if ("\u0000".equals(s)) {
            return true;
        }
        return s.length() == 0 || "".equals(s);
    }

    /**
     * 是否存在某个字符串
     *
     * @param s     字符串
     * @param regex 需要查找的字符串(支持正则)
     * @return 返回boolean
     */
    public static boolean isExist(String s, String regex) {
        if (s.contains(regex)) return true;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        return matcher.find();
    }

    /**
     * 获取最后一个字符
     *
     * @param v 目标字符串
     * @return 返回该字符串的最后一个字符
     */
    public static String getLastString(String v) {
        return v.substring(v.length() - 1);
    }

    /**
     * 删除最后一个字符
     *
     * @param v 目标字符串
     * @return 返回处理后的字符串
     */
    public static String removeLastString(String v) {
        return v.substring(0, v.length() - 1);
    }

    /**
     * 判断当前字符串是不是数字
     *
     * @param v 目标字符串
     * @return 返回boolean
     */
    public static boolean isNumber(String v) {
        return v.matches("^[0-9]*$");
    }

    /**
     * 删除所有非数字的字符
     *
     * @param v 目标字符串
     * @return 返回处理后的字符串
     */
    public static String removeNotNumber(String v) {
        return v.replaceAll("[^\\d]", "");
    }

    /**
     * 获取一个字符串的开始位置和结束位置
     *
     * @param v    源字符串
     * @param find 需要查找的字符串
     * @return 数组 0=开始位置 1=结束位置
     */
    public static int[] getStartAndEndIndex(String v, String find) {
        int start = -1; // 开始下标
        int end = -1; // 结束下标
        int currentIndex = 0; // 当前下标 -1等于当前源字符串长度已遍历完
        char[] source = v.toCharArray(); // 源字符串char数组
        char[] target = find.toCharArray();
        while (source.length >= currentIndex) {
            boolean result = true;
            for (int i = 0; i < target.length; i++) {
                if (source[currentIndex] == target[i]) {
                    result = true;
                } else {
                    result = false;
                    currentIndex++;
                    break;
                }
                currentIndex++;
            }
            if (result) {
                start = currentIndex - target.length;
                end = currentIndex;
                break;
            }
        }
        return new int[]{start, end};
    }

    /**
     * 获取文件名后缀
     *
     * @param filename 文件名
     * @return 后缀
     */
    public static String getSuffix(String filename) {
        int suffix = filename.lastIndexOf(".");
        if (suffix == -1) return null;
        return filename.substring(suffix);
    }

    /**
     * 删除后缀
     *
     * @param filename 文件名
     * @return 删除后缀后的文件名
     */
    public static String removeSuffix(String filename) {
        return filename.substring(0, filename.lastIndexOf("."));
    }

    /**
     * 格式化
     *
     * @param input
     * @param args
     * @return
     */
    public static String format(String input, Object... args) {
        int offset = 0;
        int subscript = 0;
        char[] chars = input.toCharArray();
        StringBuilder builder = new StringBuilder();
        char previous = '#';
        for (int i = 0; i < chars.length; i++) {
            char current = chars[i];
            if (previous == '{' && current == '}') {
                char[] temp = new char[(i - offset) - 1];
                System.arraycopy(chars, offset, temp, 0, (offset = i - 1));
                builder.append(temp).append(args[subscript]);
                temp = new char[chars.length - offset - 2];
                System.arraycopy(chars, offset + 2, temp, 0, temp.length);
                // reset
                chars = temp;
                subscript ++;
                i        = 0;
                offset   = 0;
            } else {
                previous = current;
            }
        }
        return builder.append(chars).toString();
    }

}

