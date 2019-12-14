package com.poseidon.framework.tools;

import java.io.Serializable;

/**
 * 为了方便能够以行读取数据设计的String类
 *
 * Create by 2BKeyboard on 2019/12/11 23:16
 */
public abstract class StringNewline
        implements Serializable {

    /**
     * 所有String都会被转换成char数组存放
     */
    protected char[] value;

    /**
     * 指针指向value数组下一个元素插入的位置
     */
    protected int valuePointer = 0;

    /**
     * 记录每个换行符的位置
     */
    protected int[] line;

    /**
     * 指针指向line数组下一个元素插入的位置
     */
    protected int linePointer = 0;

    /**
     * 记录迭代器当前遍历到第几行了
     */
    protected int hasNext = -1;

    /**
     * 默认构造器
     * 会初始化value数组大小以及line数组的大小
     * 默认大小为 value: 16, line: 8
     */
    public StringNewline() {
        this(16, 8);
    }

    /**
     * 传入value数组初始化大小，line数组默认大小为value数组的一半
     * @param size value数组大小
     */
    public StringNewline(int size) {
        this(size, size >> 1);
    }

    /**
     * 传入value数组初始化大小以及line数组的初始化大小
     * @param valueCapacity value数组初始化大小
     * @param lineCapacity line数组初始化大小
     */
    public StringNewline(int valueCapacity, int lineCapacity) {
        this.value = new char[valueCapacity];
        this.line = new int[lineCapacity];
        this.line[0] = 0;
        linePointer++;
    }

    /**
     * 传入一个String，这个String会被解析成value数组放在value[]中
     * 在解析的过程中会遍历这个value数组中是否存在换行符('\n')，如果存在就会记录换行符存在的位置。
     *
     * @param str 字符串
     */
    public StringNewline(String str) {
        this(str.toCharArray());
    }

    /**
     * 传入value数组
     *
     * @param charArray
     */
    public StringNewline(char[] charArray) {
        this((charArray.length + 16), 6);
        System.arraycopy(charArray, 0, value, 0, charArray.length);
        this.valuePointer = charArray.length;
        for (int i = 0; i < value.length; i++) {
            if (value[i] == '\n') {
                addLineArray(i);
            }
        }
    }

    /**
     * 追加数据
     *
     * @param str 字符串
     * @return
     */
    public StringNewline append(String str) {
        return append(str.toCharArray());
    }

    /**
     * 追加数据
     *
     * @param values value数组
     * @return
     */
    public StringNewline append(char[] values) {
        capacityCheck(values);
        System.arraycopy(values, 0, this.value, valuePointer, values.length);
        this.valuePointer += values.length;
        for (int i = 0; i < values.length; i++) {
            if (values[i] == '\n') {
                addLineArray(valuePointer);
            }
        }
        return this;
    }

    /**
     * 添加一行数据
     *
     * @param str
     * @return
     */
    public StringNewline appendLine(String str) {
        append(str.concat("\n"));
        return this;
    }

    /**
     * 记录行数
     *
     * @param line
     */
    private void addLineArray(int line) {
        if (linePointer >= this.line.length) {
            lineExpansion(16);
        }
        this.line[linePointer] = line;
        linePointer++;
    }

    /**
     * 读取一行数据
     *
     * @param line 读取指定行数
     */
    public String readLine(int line) {
        if (linePointer >= line && line >= 0) {
            return toString(this.line[line], this.line[line + 1]);
        }
        throw new IndexOutOfBoundsException(String.valueOf(line));
    }

    /**
     * 从指定行插入数据（插入一行）
     *
     * @param    line               从line行插入
     * @param    str                待插入的数据
     * @return StringNewline
     */
    public StringNewline insertLine(int line, String str) {
        return insert(line, readLine(line).length(), str.concat("\n"));
    }

    /**
     * 从指定行插入数据
     *
     * @param    line               从line行插入
     * @param    str                待插入的数据
     * @return StringNewline
     */
    public StringNewline insert(int line, String str) {
        return insert(line, readLine(line).length(), str);
    }

    /**
     * 从指定行插入数据
     *
     * @param    line               从line行开始
     * @param    pos                从指定位置开始
     * @param    str                待插入的数据
     *
     * @return StringNewline
     */
    public StringNewline insert(int line, int pos, String str) {
        int strLen = str.length();
        capacityCheck(strLen);
        int offset = 0;
        int linePointerValue = linePointer - 1;
        if (line > linePointerValue || line < 0)
            throw new IndexOutOfBoundsException("line:\"" + line + "\"");
        if (line <= linePointerValue) {
            offset = this.line[line] + pos;
        }
        char[] strValue = str.toCharArray();
        char[] temp = new char[(strLen << 1) + this.value.length + 16];
        char[] end = new char[(valuePointer - offset)];
        System.arraycopy(value, 0, temp, 0, offset);
        System.arraycopy(value, offset, end, 0, end.length);
        System.arraycopy(strValue, 0, temp, offset, strValue.length);
        offset = offset + strLen;
        System.arraycopy(end, 0, temp, offset, end.length);
        this.value = temp;
        for (int i = line + 1; i < linePointer; i++) {
            this.line[i] = this.line[i] + strValue.length;
        }
        int insertLine = line;
        for (int i = 0; i < strValue.length; i++) {
            if (strValue[i] == '\n') {
                int value = this.line[line] + pos + i + 1;
                this.line = ArrayUtils.insert(insertLine, value, this.line);
                insertLine++;
                linePointer++;
            }
        }
        valuePointer = valuePointer + strLen;
        return this;
    }

    /**
     * 迭代器
     *
     * @return
     */
    public boolean hasNext() {
        int len = linePointer - 2;
        return (this.hasNext = this.hasNext + 1) <= len;
    }

    /**
     * 获取当前遍历的行
     *
     * @return
     */
    public String next() {
        return readLine(this.hasNext);
    }

    /**
     * 在迭代状态下当前迭代到第几行了
     * @return 当前行号
     */
    public int getCurrentLineNumber() {
        return this.hasNext;
    }

    /**
     * value数组扩容
     *
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
     * 容量检测，如果不够了则进行扩容
     */
    private void capacityCheck(int size) {
        int remainCapacity = getValueRemainSpace(); // 剩余容量
        if (size > remainCapacity) {
            valueExpansion(this.value.length + size + remainCapacity + 16);
        }
    }

    private void capacityCheck(char[] value){
        capacityCheck(value.length);
    }

    private void capacityCheck(String str){
        capacityCheck(str.length());
    }

    /**
     * line数组扩容
     *
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
     *
     * @return
     */
    private int getValueRemainSpace() {
        return this.value.length - valuePointer-1;
    }

    /**
     * 转换为char数组
     * @return char[]
     */
    public char[] toCharArray() {
        return this.value;
    }

    /**
     * 截取指定行并转换为char[]
     *
     * @param startLinePos 开始位置
     * @param endLinePos   结束位置
     * @return char Array
     */
    public char[] toCharArray(int startLinePos, int endLinePos) {
        int intValue = endLinePos - startLinePos;
        char[] charArray = new char[intValue];
        System.arraycopy(value, startLinePos, charArray, 0, intValue);
        return charArray;
    }

    /**
     * 截取指定行
     * @param startLinePos 开始位置
     * @param endLinePos 结束位置
     * @return String object
     */
    public String toString(int startLinePos, int endLinePos) {
        return new String(toCharArray(startLinePos, endLinePos)).replaceAll("\n", "");
    }

    /**
     * 将整个value数组转换成字符串
     * @return String object
     */
    @Override
    public String toString() {
        char[] result = new char[valuePointer];
        System.arraycopy(value, 0, result, 0, valuePointer);
        return new String(result);
    }

}
