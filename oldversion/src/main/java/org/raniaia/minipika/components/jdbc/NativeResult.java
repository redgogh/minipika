package org.jiakesiws.minipika.components.jdbc;

/* ************************************************************************
 *
 * Copyright (C) 2020 2B键盘 All rights reserved.
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
 *
 * ************************************************************************/

/*
 * Creates on 2019/11/13.
 */

import java.sql.ResultSet;
import java.util.List;

/**
 * 封装原生结果集，用于缓存结果从而使ResultSet提前关闭。
 *
 * @author 2B键盘
 * @email jiakesiws@gmail.com
 */
public interface NativeResult {

    /**
     * 构建NativeResultSet
     * @param rset
     * @return
     */
    NativeResult build(ResultSet rset);

    /**
     * 将查询结果转换为实体类
     * @param target
     * @param <T>
     * @return
     */
    <T> T conversionJavaBean(Class<T> target);

    /**
     * 将查询结果转换为集合
     * @param target
     * @param <T>
     * @return
     */
    <T> List<T> conversionJavaList(Class<T> target);

    /**
     * 将查询结果转换为json字符串
     * @return
     */
    String toJSONString();

    /**
     * 下一条数据
     */
    void hasNext();

    /**
     * 下一个值
     * @return
     */
    String next();

    /**
     * 重置
     */
    void reset();

}
