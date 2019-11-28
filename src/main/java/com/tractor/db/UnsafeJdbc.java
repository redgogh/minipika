package com.tractor.db;

import com.tractor.tools.TractorUtils;

/**
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/27 15:53
 * @since 1.8
 */
public class UnsafeJdbc extends NativeJdbcAutoCommit implements UnsafeJdbcService{

    @Override
    public int clear(Class<?> target) {
        String table = TractorUtils.getModelValue(target);
        return executeUpdate(String.format("truncate table %s",table));
    }

}
