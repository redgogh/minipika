package com.tractor.framework.db;

import com.tractor.framework.tools.TractorUtils;

/**
 * Create by 2BKeyboard n 2019/11/27 15:53
 */
public class UnsafeJdbc extends JdbcSupport implements UnsafeJdbcService{

    @Override
    public int clear(Class<?> target) {
        String table = TractorUtils.getModelValue(target);
        return update(String.format("truncate table %s",table));
    }

}
