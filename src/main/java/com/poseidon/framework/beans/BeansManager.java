package com.poseidon.framework.beans;

import com.poseidon.customize.ConnectionPool;
import com.poseidon.framework.cache.PoseidonCache;
import com.poseidon.framework.db.JdbcSupport;
import com.poseidon.framework.db.NativeResult;
import com.poseidon.framework.annotation.Valid;
import com.poseidon.framework.cache.PoseidonCacheImpl;
import com.poseidon.framework.db.*;

import java.sql.ResultSet;

/**
 * 管理接口的创建
 * Create by 2BKeyboard on 2019/11/28 17:25
 */
public class BeansManager {

    private static JdbcSupport jdbcSupport;
    private static ConnectionPool connectionPool;
    private static PoseidonCache poseidonCache;

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
        if(jdbcSupport == null){
            jdbcSupport = new JdbcSupportImpl();
        }
        return jdbcSupport;
    }

    @Valid
    public static PoseidonCache getPoseidonCache(){
        if(poseidonCache == null){
            poseidonCache = new PoseidonCacheImpl();
        }
        return poseidonCache;
    }

    @Valid
    public static ConnectionPool getConnPool(){
        if(connectionPool == null){
            connectionPool = new com.poseidon.framework.pool.ConnectionPool();
        }
        return connectionPool;
    }

}
