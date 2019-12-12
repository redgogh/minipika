package com.poseidon.framework.sql.pte.generate;

import lombok.Data;

/**
 * 记录字段标记位置
 *
 * {@code name}         字段代表标记字段的名字,比如：[#username],那么name字段就是username
 * {@code start}        字段代表name出现的开始位置
 * {@code end}          字段代表name结束位置
 * {@code line}         字段所在行
 *
 * Create by 2BKeyboard on 2019/12/12 17:29
 */
@Data
public class Position {

    /**
     * 名称
     */
    private String name;

    /**
     * 开始位置
     */
    private int start;

    /**
     * 结束位置
     */
    private int end;

    /**
     * 所在行
     */
    private int line;

    public Position(String name, int start, int end,int line) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.line = line;
    }
}
