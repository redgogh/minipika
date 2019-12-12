package com.poseidon.framework.tools;

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
    private int hasNext = -1;

    /**
     * 默认构造器
     * 会初始化value数组大小以及line数组的大小
     * 默认大小为 value: 16, line: 8
     */
    public PteString() {
        this(16, 8);
    }

    /**
     * 传入value数组初始化大小，line数组默认大小为value数组的一半
     * @param size value数组大小
     */
    public PteString(int size) {
        this(size, size >> 1);
    }

    /**
     * 传入value数组初始化大小以及line数组的初始化大小
     * @param valueCapacity value数组初始化大小
     * @param lineCapacity line数组初始化大小
     */
    public PteString(int valueCapacity, int lineCapacity) {
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
    public PteString(String str) {
        this(str.toCharArray());
    }

    /**
     * 传入value数组
     *
     * @param charArray
     */
    public PteString(char[] charArray) {
        this((charArray.length + 16), 6);
        System.arraycopy(charArray, 0, value, 0, charArray.length);
        this.valuePointer = charArray.length;
        for (int i = 0; i < value.length; i++) {
            if (value[i] == '\n') {
                addLine(i);
            }
        }
    }

    /**
     * 追加数据
     *
     * @param str 字符串
     * @return
     */
    public PteString append(String str) {
        return append(str.toCharArray());
    }

    /**
     * 追加数据
     *
     * @param values value数组
     * @return
     */
    public PteString append(char[] values) {
        int len = values.length;
        if (getValueRemainSpace() < len) {
            //
            // 扩容len的两倍
            //
            valueExpansion(valuePointer + (len << 1));
        }
        System.arraycopy(values, 0, this.value, valuePointer, values.length);
        this.valuePointer += values.length;
        for (int i = 0; i < values.length; i++) {
            if (values[i] == '\n') {
                addLine(valuePointer);
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
    public PteString appendLine(String str) {
        append(str.concat("\n"));
        return this;
    }

    /**
     * 记录行数
     *
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
     *
     * @param line 读取指定行数
     */
    public String readLine(int line) {
        if (linePointer >= line && line >= 0) {
            return toString(this.line[line], this.line[line + 1]);
        }
        throw new IndexOutOfBoundsException(String.valueOf(line));
    }

    public static void main(String[] args) {
        PteString pteString = new PteString();
        pteString.appendLine("aaaa");
        pteString.appendLine("bbbb");
        pteString.appendLine("cccc");

        System.out.println(pteString.readLine(0));
        System.out.println(pteString.readLine(1));
        System.out.println(pteString.readLine(2));

        System.out.println("---------------");

        while (pteString.hasNext()) {
            System.out.println(pteString.next());
        }

        System.out.println(0 -1 );

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
     * 在迭代状态下当前迭代到第几行了
     * @return 当前行号
     */
    public int getCurrentLineNumber() {
        return this.hasNext;
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
     * 截取指定行
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

    /**
     * 截取指定行数转换成新的PteString
     *
     * @param startLinePos 开始行号
     * @param endLinePos   结束行号
     * @return new PteString
     */
    public PteString toPteString(int startLinePos, int endLinePos) {
        return new PteString(toCharArray(line[startLinePos]+1, line[endLinePos]-1));
    }

    /**
     * 获取从开始行号到最后一行
     * @param startLinePos 开始行号
     * @return new PteString
     */
    public PteString toPteString(int startLinePos) {
        return toPteString(startLinePos, (linePointer - 1));
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
        return this.value.length - valuePointer;
    }

}
