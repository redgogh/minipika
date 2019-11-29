package com.tractor.framework.db;

import com.tractor.framework.config.Config;

/**
 * NativeJdbcAutoCommit
 *
 * @author 2BKeyboard
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
