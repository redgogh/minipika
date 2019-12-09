package com.poseidon.framework.beans;

import com.poseidon.customize.ConnectionPool;
import com.poseidon.framework.annotation.Valid;
import com.poseidon.framework.cache.PoseidonCache;
import com.poseidon.framework.db.*;

import java.sql.ResultSet;

/**
 * 管理接口的创建
 * Create by 2BKeyboard on 2019/11/28 17:25
 */
public class BeansManager {

    /* -------------------------- NEW ---------------------------- **/

    @Valid
    public static NativeJdbc newNativeJdbc() {
        return new NativeJdbcImpl();
    }

    @Valid
    public static NativeResult newNativeResult() {
        return new NativeResultMysql();
    }

    @Valid
    public static NativeResult newNativeResult(ResultSet resultSet) {
        return newNativeResult().build(resultSet);
    }

    /* -------------------------- GET ---------------------------- **/

    @Valid
    public static JdbcSupport getJdbcSupport(){
        return JdbcSupport.getTemplate();
    }

    @Valid
    public static PoseidonCache getPoseidonCache(){
        return PoseidonCache.getCache();
    }

    @Valid
    public static ConnectionPool getConnPool(){
        return com.poseidon.framework.pool.ConnectionPool.getPool();
    }

}
