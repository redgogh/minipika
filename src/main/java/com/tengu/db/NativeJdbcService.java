package com.tengu.db;

import java.sql.ResultSet;

/**
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/13 16:57
 * @since 1.8
 */
public interface NativeJdbcService {

    /**
     * 执行查询
     * @param sql
     * @param args
     * @return
     */
    ResultSet executeQuery(String sql,Object... args);

}
