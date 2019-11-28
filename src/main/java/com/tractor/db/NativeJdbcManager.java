package com.tractor.db;

import com.tractor.config.Config;

/**
 * NativeJdbcAutoCommit
 *
 * @author 2Bkeyboard
 * @version 1.0.0
 * @date 2019/11/28 17:25
 * @since 1.8
 */
public class NativeJdbcManager {

    public static NativeJdbc createNativeJdbc() {
        if (Config.getTransaction()) {
            return new NativeJdbcManualCommit();
        } else {
            return new NativeJdbcAutoCommit();
        }
    }

}
