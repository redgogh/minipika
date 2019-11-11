package com.notfound.db;

import java.util.List;

/**
 * @author 404NotFoundx
 * @date 2019/11/11 23:40
 * @version 1.0.0
 * @since 1.8
 */
public interface AbsJdbcTemplate {

    /**
     * 查询单个结果
     * @param sql sql语句
     * @param obj 需要返回的对象
     * @param args 参数列表
     * @param <T>
     * @return 封装好的对象
     */
    <T> T queryForObject(String sql, T obj, Object... args);

    /**
     * 查询多个结果
     * @param sql sql语句
     * @param obj 需要返回的对象
     * @param args 参数列表
     * @param <T>
     * @return 封装好的结果集
     */
    <T> List<T> queryForList(String sql, T obj, Object... args);

    /**
     * 更新所有实体类中的所有数据，但不包括为空的数据。
     * @param obj 实体类
     * @return 更新条数
     */
    long update(Object obj);

    /**
     * 通过SQL语句来更新数据。
     * @param sql sql语句
     * @param args 参数列表
     * @return 更新条数
     */
    long update(String sql,Object... args);

    /**
     * 传入一个实体类，将实体类中为空的数据也进行更新。
     * @param obj 实体类
     * @return 更新条数
     */
    long updateDoNULL(Object obj);

    /**
     * 通过SQL模板来更新数据
     * @param temp sql模板
     * @return 更新条数
     */
    long update(SQLTemplate temp);

    /**
     * 通过sql语句插入一条数据
     * @param sql sql语句
     * @param args 参数列表
     * @return 更新条数
     */
    long insert(String sql,Object... args);

    /**
     * 通过SQLTemplate更新数据
     * @param temp SQLTemplate
     * @return 更新条数
     */
    long insert(SQLTemplate temp);

    /**
     * 通过实体类来更新数据
     * @param model 实体类
     * @return 更新条数
     */
    <T> long insert(T model);

    /**
     * 通过sql语句进行删除
     * @param sql sql语句
     * @param args 参数列表
     * @return 更新条数
     */
    long delete(String sql,Object... args);

    /**
     * 根据SQLTemplate进行删除
     * @param temp SQLTemplate
     * @return 更新条数
     */
    long delete(SQLTemplate temp);

    /**
     * 根据主键进行删除
     * @param id 主键
     * @return 更新条数
     */
    long delete(String id);

}
