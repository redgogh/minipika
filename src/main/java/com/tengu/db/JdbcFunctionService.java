package com.tengu.db;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/13 16:43
 * @since 1.8
 */
 interface JdbcFunctionService {

    /**
     * 查询并返回对象
     * @param sql
     * @param obj
     * @param args
     * @param <T>
     * @return
     */
     <T> T queryForObject(String sql, Class<T> obj, Object... args);

    /**
     * 查询多个结果
     * @param sql sql语句
     * @param obj 需要返回的对象
     * @param args 参数列表
     * @param <T>
     * @return 封装好的结果集
     */
     <T> List<T> queryForList(String sql, Class<T> obj, Object... args);

    /**
     * 更新所有实体类中的所有数据，但不包括为空的数据。
     * @param obj 实体类
     * @return 更新条数
     */
     Integer update(Object obj);

    /**
     * 通过SQL语句来更新数据。
     * @param sql sql语句
     * @param args 参数列表
     * @return 更新条数
     */
     Integer update(String sql, Object... args);

    /**
     * 传入一个实体类，将实体类中为空的数据也进行更新。
     * @param obj 实体类
     * @return 更新条数
     */
     Integer updateDoNULL(Object obj);

    /**
     * 通过sql语句插入一条数据
     * @param sql sql语句
     * @param args 参数列表
     * @return 更新条数
     */
     Integer insert(String sql, Object... args);

    /**
     * 通过实体类来更新数据
     * @param model 实体类
     * @return 更新条数
     */
     Integer insert(Object model);

    /**
     * 通过sql语句进行删除
     * @param sql sql语句
     * @param args 参数列表
     * @return 更新条数
     */
     Integer delete(String sql, Object... args);

    /**
     * 查询某张表所有字段
     * @param tableName
     */
    ArrayList<String> getColumns(String tableName);

    /**
     * 执行自定义SQL
     * @param sql
     * @return
     */
    boolean execute(String sql);

}
