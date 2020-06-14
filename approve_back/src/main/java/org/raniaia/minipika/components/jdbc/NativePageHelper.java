package org.jiakesiws.minipika.components.jdbc;

/*
 * Copyright (C) 2020 Tiansheng All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * Creates on 2019/12/2.
 */

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 分页插件基类
 * 由于扩展原因，所以NativePageHelper是抽象类
 * 子类可以去继承NativePageHelper,并实现自己的功能
 *
 * @author tiansheng
 */
@Getter
@Setter
public abstract class NativePageHelper {

    protected int total;            // 总页数
    protected int records;          // 总记录数
    protected int pageNum;          // 当前显示第几页
    protected int pageSize;         // 每页显示多少条
    protected List<?> data;         // 分页数据

    private Class<?> generic;

    public NativePageHelper(Class<?> generic) {
        this(0, 0, generic);
    }

    public NativePageHelper(int pageNum, int pageSize, Class<?> generic) {
        this.pageNum = pageNum;
        this.generic = generic;
        this.pageSize = pageSize;
    }

    public int getTotal() {
        total = records / pageSize;
        return total;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
