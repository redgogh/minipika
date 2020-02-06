package org.laniakeamly.poseidon.framework.tools;

/**
 * 可以进行一行一行读取的String类
 * 已经有一个实现类了{@link StringNewline}
 * 不过这个实现类已经准备移除了,bug有点多,当前存在的意义就是只有参考
 *
 * Copyright: Create by 2BKeyboard on 2019/12/17 11:48
 */
public interface LineBuilder {

    /**
     * 追加数据
     *
     * @param str 字符串
     * @return
     */
    LineBuilder append(String str);

    /**
     * 追加数据
     *
     * @param values value数组
     * @return
     */
    LineBuilder append(char[] values);

    /**
     * 添加一行数据
     *
     * @param str
     * @return
     */
    LineBuilder appendLine(String str);

    /**
     * 读取一行数据
     *
     * @param line 读取指定行数
     */
    String readLine(int line);

    /**
     * 从指定行插入数据（插入一行）
     *
     * @param    line               从line行插入
     * @param    str                待插入的数据
     * @return StringNewline
     */
    LineBuilder insertLine(int line, String str);

    /**
     * 从指定行插入数据
     *
     * @param    line               从line行插入
     * @param    str                待插入的数据
     * @return StringNewline
     */
    LineBuilder insert(int line, String str);

    /**
     * 从指定行插入数据
     *
     * @param    line               从line行开始
     * @param    pos                从指定位置开始
     * @param    str                待插入的数据
     *
     * @return StringNewline
     */
    LineBuilder insert(int line, int pos, String str);

    /**
     * 迭代器,每调用一次代表指针移动到下一行
     * @return 是否是最后一行如果不是为true
     */
    boolean hasNext();

    /**
     * 获取当前遍历的行
     *
     * @return
     */
    String next();

    /**
     * 在迭代状态下当前迭代到第几行了
     * @return 当前行号
     */
    int getCurrentLineNumber();

    /**
     * 转换为char数组
     *
     * @return Array
     */
    char[] toCharArray();

    /**
     * 截取指定行并转换为char[]
     *
     * @param startLinePos 开始位置
     * @param endLinePos   结束位置
     *
     * @return char Array
     */
    char[] toCharArray(int startLinePos, int endLinePos);

    /**
     * 截取指定行
     * @param startLinePos 开始位置
     * @param endLinePos 结束位置
     *
     * @return String object
     */
    String toString(int startLinePos, int endLinePos);

    /**
     * @return String
     */
    String toString();

}
