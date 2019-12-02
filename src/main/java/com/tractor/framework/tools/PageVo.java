package com.tractor.framework.tools;

import lombok.Data;

import java.util.List;

/**
 * 分页插件
 * Create by 2BKeyboard on 2019/12/2 21:01
 */
@Data
public class PageVo<T> {

    protected List<T> data;         // 分页数据
    protected Integer total;        // 总页数
    protected Integer records;      // 总记录数
    protected Integer pageNum;      // 当前显示第几页
    protected Integer pageSize;     // 每页显示多少条

    public Integer getTotal() {
        total = records / pageSize;
        return total;
    }

}
