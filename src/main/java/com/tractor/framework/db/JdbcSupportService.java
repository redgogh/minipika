package com.tractor.framework.db;

import java.util.List;

/**
 * 增删改查操作
 * @author 2BKeyboard
 * @version 1.0.0
 * @date 2019/11/11 23:40
 * @since 1.8
 */
public interface JdbcSupportService {

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
     * 查询并返回JSON字符串
     * @param sql
     * @param args
     * @return
     */
    String queryForJson(String sql, Object... args);

    /**
     * 分页查询,SQL不用加limit
     * @param sql
     * @param args
     * @return
     */
    NativePageVo queryForPageVo(String sql, NativePageVo pageVo, Object... args);

    /**
     * 查询并返回结果集
     * @param sql
     * @param args
     * @return
     */
    NativeResult queryForResult(String sql, Object... args);

    /**
     * 更新所有实体类中的所有数据，但不包括为空的数据。
     * @param obj 实体类
     * @return 更新条数
     */
    int updateForModel(Object obj);

    /**
     * 通过SQL语句来更新数据。
     * @param sql sql语句
     * @param args 参数列表
     * @return 更新条数
     */
    int update(String sql, Object... args);

    /**
     * 传入一个实体类，将实体类中为空的数据也进行更新。
     * @param obj 实体类
     * @return 更新条数
     */
    int updateDoNULL(Object obj);

    /**
     * 通过sql语句插入一条数据
     * @param sql sql语句
     * @param args 参数列表
     * @return 更新条数
     */
    int insert(String sql, Object... args);

    /**
     * 通过实体类来更新数据
     * @param model 实体类
     * @return 更新条数
     */
    int insert(Object model);

    /**
     * 统计单张表的所有数据
     * @param target 传入UserModel
     * @return
     */
    long count(Class<?> target);

    /**
     * 统计sql查询到的所有数据
     * @param sql
     * @return
     */
    long count(String sql,Object... args);

    /**
     * 执行sql
     * @param sql
     * @param args
     * @return
     */
    boolean execute(String sql, Object... args);

    /**
     * 查询某张表所有字段
     * @param tableName
     */
    List<String> getColumns(String tableName);

}
