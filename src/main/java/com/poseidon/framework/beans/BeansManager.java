package com.poseidon.framework.beans;

import com.poseidon.framework.cache.PoseidonCache;
import com.poseidon.framework.db.*;

import java.sql.ResultSet;
import java.util.Map;

/**
 * 管理接口的创建，类似于spring的bean，只不过不是注解形式
 * Create by 2BKeyboard on 2019/11/28 17:25
 */
public class BeansManager {

    private static Map<String,Object> beans;

    public static NativeJdbc newNativeJdbc() {
        return new NativeJdbcImpl();
    }

    public static JdbcSupport newJdbcSupport(){
        return JdbcSupport.getTemplate();
    }

    public static NativeResult newNativeResult() {
        return new NativeResultMysql();
    }

    public static NativeResult newNativeResult(ResultSet resultSet) {
        return newNativeResult().build(resultSet);
    }

    public static PoseidonCache getPoseidonCache(){
        return PoseidonCache.getCache();
    }

}
