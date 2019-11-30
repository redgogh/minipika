package com.tractor.framework.db;

import java.sql.ResultSet;

/**
 * NativeJdbcAutoCommit
 *
 * @author 2BKeyboard
 * @version 1.0.0
 * @date 2019/11/28 17:25
 * @since 1.8
 */
public class NativeBeans {

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
