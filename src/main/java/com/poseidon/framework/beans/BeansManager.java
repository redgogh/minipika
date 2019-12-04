package com.poseidon.framework.beans;

import com.poseidon.framework.db.NativeJdbc;
import com.poseidon.framework.db.NativeJdbcImpl;
import com.poseidon.framework.db.NativeResult;
import com.poseidon.framework.db.NativeResultMysql;

import java.sql.ResultSet;

/**
 * 管理接口的创建，类似于spring的bean，只不过不是注解形式
 * Create by 2BKeyboard on 2019/11/28 17:25
 */
public class BeansManager {

    public static NativeJdbc newNativeJdbc() {
        return new NativeJdbcImpl();
    }

    public static NativeResult newNativeResult() {
        return new NativeResultMysql();
    }

    public static NativeResult newNativeResult(ResultSet resultSet) {
        return newNativeResult().build(resultSet);
    }

}
