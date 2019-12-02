package com.tractor.framework.cache;

import com.tractor.framework.db.NativeResult;

/**
 * 缓存
 * Create by 2BKeyboard on 2019/11/29 17:16
 */
public interface NativeCache {

    /**
     * 获取缓存
     * @param key
     */
    void get(String key);

    /**
     * 保存或更新缓存
     * @param key
     * @param value
     */
    void save(String key,NativeResult value);

    /**
     * 保存或更新缓存，添加缓存过期时间
     * @param key
     * @param value
     * @param expiredTime 过期时间，以毫秒为单位
     */
    void save(String key,NativeResult value,Long expiredTime);

}
