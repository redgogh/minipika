package com.tengu.db;

import java.sql.ResultSet;
import java.util.List;

/**
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/20 17:38
 * @since 1.8
 */
public interface NativeResultSetService {

    /**
     * 构建NativeResultSet
     * @param rset
     * @return
     */
    NativeResultSet build(ResultSet rset);

    /**
     * 将查询结果转换为实体类
     * @param target
     * @param <T>
     * @return
     */
    <T> T conversionJavaBean(Class<T> target);

    /**
     * 将查询结果转换为集合
     * @param target
     * @param <T>
     * @return
     */
    <T> List<T> conversionJavaList(Class<T> target);

    /**
     * 将查询结果转换为json字符串
     * @return
     */
    String toJSONString();

}
