package org.jiakesiws.minipika.components.cache;

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
 * Creates on 2019/12/9.
 */

import org.jiakesiws.minipika.components.jdbc.NativeResult;

/**
 * 缓存接口.
 *
 * Cache interface.
 *
 * @author 2B键盘
 * @version 1.0.0
 * @since 1.8
 */
public interface MinipikaCache {

    /**
     * 获取缓存
     * @param sql
     * @param args
     * @return
     */
    NativeResult get(String sql, Object... args);

    /**
     * 保存
     * @param sql
     * @param result
     * @param args
     * @return
     */
    void save(String sql, NativeResult result, Object... args);

    /**
     * 刷新缓存
     * @param sql
     */
    void refresh(String sql);

    /**
     * 刷新所有缓存
     */
    void refreshAll();

}
