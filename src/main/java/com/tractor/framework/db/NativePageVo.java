package com.tractor.framework.db;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 分页插件
 * Create by 2BKeyboard on 2019/12/2 21:01
 */
@Getter
@Setter
public class NativePageVo<T> {

    protected int total;            // 总页数
    protected int records;          // 总记录数
    protected int pageNum;          // 当前显示第几页
    protected int pageSize;         // 每页显示多少条
    protected List<T> data;         // 分页数据
    private Class<?> generic;

    public int getTotal() {
        total = records / pageSize;
        return total;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
