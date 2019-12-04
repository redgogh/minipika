package com.poseidon.framework.db;

import com.poseidon.framework.tools.StringUtils;
import com.poseidon.framework.tools.PoseidonUtils;

/**
 * Create by 2BKeyboard n 2019/11/27 15:53
 */
public class UnsafeJdbc extends JdbcSupport implements UnsafeJdbcService{

    @Override
    public int clear(Class<?> target) {
        String table = PoseidonUtils.getModelValue(target);
        return update(StringUtils.format("truncate table {}",table));
    }

}
