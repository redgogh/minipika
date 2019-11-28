package com.tractor.db;

import com.tractor.config.Config;

/**
 * NativeJdbcAutoCommit 管理
 *
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/28 17:25
 * @since 1.8
 */
public class NativeJdbcManager {

    private static NativeJdbc nativeJdbc;
    private static final Boolean transaction = Config.getTransaction();

    public static NativeJdbc getNativeJdbc() {
        if (nativeJdbc == null) {
            if (transaction) {
                nativeJdbc = new NativeJdbcManualCommit();
            } else {
                nativeJdbc = new NativeJdbcAutoCommit();
            }
        }
        return nativeJdbc;
    }

}
