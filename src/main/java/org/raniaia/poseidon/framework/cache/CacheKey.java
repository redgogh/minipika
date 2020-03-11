package org.raniaia.poseidon.framework.cache;

import lombok.Getter;
import lombok.Setter;
import org.raniaia.poseidon.framework.db.NativeJdbc;

import java.util.List;

/**
 *
 * Chinese:
 *
 * 缓存KEY对象
 * 这个对象中包含CacheKey的唯一值，就是key字段
 *
 * 然后对象中还包含tables，实现缓存自动刷新是通过tables进行刷新的。
 * 也就是说如果查询了user表，tables这个List对象中就会存放一个user字段。
 * 当缓存更新时就会去遍历哪个CacheKey中存在user表，把这些存在user表的缓存都刷新掉。
 *
 * English:
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
 * Copyright: Create by TianSheng on 2019/12/6 16:24
 *
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
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
