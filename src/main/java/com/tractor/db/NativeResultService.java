package com.tractor.db;

import java.sql.ResultSet;
import java.util.List;

/**
 * 封装原生结果集，用于缓存结果从而使ResultSet提前关闭。
 * @author 2Bkeyboard
 * @version 1.0.0
 * @date 2019/11/13 18:12
 * @since 1.8
 */
public interface NativeResultService {

    /**
     * 构建NativeResultSet
     * @param rset
     * @return
     */
    NativeResult build(ResultSet rset);

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

    /**
     * 下一条数据
     */
    void hasNext();

    /**
     * 下一个值
     * @return
     */
    String next();

    /**
     * 重置
     */
    void reset();

}
