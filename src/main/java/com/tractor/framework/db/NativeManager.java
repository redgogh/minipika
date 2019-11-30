package com.tractor.framework.db;

import com.tractor.framework.db.NativeJdbc;
import com.tractor.framework.db.NativeJdbcImpl;
import com.tractor.framework.db.NativeResult;
import com.tractor.framework.db.NativeResultMysql;

import java.sql.ResultSet;

/**
 * NativeJdbcAutoCommit
 *
 * @author 2BKeyboard
 * @version 1.0.0
 * @date 2019/11/28 17:25
 * @since 1.8
 */
public class NativeManager {

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
