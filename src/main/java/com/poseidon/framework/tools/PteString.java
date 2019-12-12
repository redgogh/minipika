package com.poseidon.framework.tools;

import lombok.Getter;

/**
 * 为了能够一行一行读取数据设计的String类
 * Create by 2BKeyboard on 2019/12/11 23:16
 */
public class PteString {

    /**
     * 所有String都会被转换成char数组存放
     */
    private char[] value;

    /**
     * 指针指向value数组下一个元素插入的位置
     */
    private int valuePointer = 0;

    /**
     * 记录每个换行符的位置
     */
    private int[] line;

    /**
     * 指针指向line数组下一个元素插入的位置
     */
    private int linePointer = 0;

    /**
     * 记录迭代器当前遍历到第几行了
     */
    @Getter
    private int hasNext = -1;

    /**
     * 空的构造方法，该方法会初始化value[]和line[]
     */
    public PteString() {
        this.value = new char[16];
        this.line = new int[8];
    }

    /**
     * 传入一个String，这个String会被解析成char数组放在value[]中
     * 在解析的过程中会遍历这个char数组中是否存在换行符('\n')，如果存在就会记录换行符存在的位置。
     *
     * @param str   字符串
     */
    public PteString(String str) {
        this.line = new int[6];
        this.value = new char[str.length() << 1];
        System.arraycopy(str.toCharArray(), 0, value, 0, str.length());
        this.valuePointer = str.length();
        if (str.contains("\n")) {
            for (int i = 0; i < value.length; i++) {
                if (value[i] == '\n') {
                    addLine(i);
                }
            }
        }
    }

    /**
     * 追加数据
     * @param str 字符串
     * @return
     */
    public PteString append(String str) {
        int len = str.length();
        if (getValueRemainSpace() < len) {
            //
            // 扩容len的两倍
            //
            valueExpansion(valuePointer + (len << 1));
        }
        return append(str.toCharArray());
    }

    /**
     * 追加数据
     *
     * @param values char数组
     * @return
     */
    public PteString append(char[] values) {
        System.arraycopy(values, 0, this.value, valuePointer, values.length);
        this.valuePointer += values.length;
        for (int i = 0; i < values.length; i++) {
            if (values[i] == '\n') {
                addLine(valuePointer - values.length);
            }
        }
        return this;
    }

    /**
     * 添加一行数据
     * @param str
     * @return
     */
    public PteString appendLine(String str){
        append(str.concat("\n"));
        return this;
    }

    /**
     * 记录行数
     * @param line
     */
    private void addLine(int line) {
        if (linePointer >= this.line.length) {
            lineExpansion(16);
        }
        this.line[linePointer] = line;
        linePointer++;
    }

    /**
     * 读取一行数据
     * @param line 读取指定行数
     */
    public String readLine(int line) {
        if (linePointer >= line && line >= 0) {
            if (line == 0) { // 第一行
                return toString(0, this.line[0]);
            }
            if (line == linePointer - 1) { // 最后一行
                return toString(this.line[line], valuePointer);
            } else {
                return toString(this.line[(line - 1)], this.line[line]);
            }
        }
        throw new IndexOutOfBoundsException(String.valueOf(line));
    }

    /**
     * 迭代器
     * @return
     */
    public boolean hasNext() {
        return (this.hasNext = this.hasNext + 1) <= linePointer;
    }

    /**
     * 获取当前遍历的行
     * @return
     */
    public String next() {
        if (this.hasNext == 0) {
            return toString(0, line[this.hasNext]);
        }
        if (this.hasNext == linePointer) {
            return toString(line[this.hasNext-1], valuePointer);
        } else {
            return toString(line[this.hasNext - 1], line[this.hasNext]);
        }
    }

    /**
     * 将char[]转换成String
     * @param startPos 开始位置
     * @param endPos 结束位置
     * @return
     */
    public String toString(int startPos, int endPos) {
        int intValue = endPos - startPos;
        char[] resultArray = new char[intValue];
        System.arraycopy(value, startPos, resultArray, 0, intValue);
        return new String(resultArray).replace("\n", "");
    }

    @Override
    public String toString() {
        char[] result = new char[valuePointer];
        System.arraycopy(value,0,result,0,valuePointer);
        return new String(result);
    }

    /**
     * value数组扩容
     * @param size 扩容大小
     */
    private void valueExpansion(int size) {
        if (size < value.length) {
            size = value.length << 1;
        }
        char[] charTemp = new char[size];
        System.arraycopy(this.value, 0, charTemp, 0, valuePointer);
        this.value = charTemp;
    }

    /**
     * line数组扩容
     * @param size 扩容大小
     */
    private void lineExpansion(int size) {
        if (size < line.length) {
            size = line.length << 1;
        }
        int[] intTemp = new int[(this.line.length + size)];
        System.arraycopy(this.line, 0, intTemp, 0, linePointer);
        this.line = intTemp;
    }

    /**
     * 获取value剩余空间
     * @return
     */
    private int getValueRemainSpace() {
        return this.value.length - valuePointer;
    }

}
