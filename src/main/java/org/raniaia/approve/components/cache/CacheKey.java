package org.raniaia.approve.components.cache;

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
 * Creates on 2019/12/6.
 */

import lombok.Getter;
import lombok.Setter;
import org.raniaia.approve.components.jdbc.NativeJdbc;

import java.util.List;

/**
 *
 * 缓存KEY对象
 * 这个对象中包含CacheKey的唯一值，就是key字段
 *
 * 然后对象中还包含tables，实现缓存自动刷新是通过tables进行刷新的。
 * 也就是说如果查询了user表，tables这个List对象中就会存放一个user字段。
 * 当缓存更新时就会去遍历哪个CacheKey中存在user表，把这些存在user表的缓存都刷新掉。
 *
 * cache key object.
 * this object contain CacheKey object the only value 'key'
 * also contain sql the tables.
 *
 * if use {@link NativeJdbc#executeUpdate(String, Object...)}
 * or {@link NativeJdbc#executeBatch(String, Object...)} method and use cache.
 *
 * in update will parser tables in sql.
 * parser out tables will compare with tables in {@code CacheKey}.
 * if tables in {@code CacheKey} contain parser out tables then refresh specify cache.
 *
 * @author tiansheng
 * @see ApproveCacheImpl#refresh
 */
public class CacheKey {

    @Getter
    @Setter
    private String key;

    @Getter
    @Setter
    private List<String> tables;

    public boolean exists(String table){
        return table.contains(table);
    }

}
