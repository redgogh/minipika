package com.tractor.framework.beans;

import com.tractor.framework.db.NativeJdbc;
import com.tractor.framework.db.NativeJdbcImpl;
import com.tractor.framework.db.NativeResult;
import com.tractor.framework.db.NativeResultMysql;

import java.sql.ResultSet;

/**
 * 管理接口的创建
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
