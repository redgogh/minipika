package com.tengu.db;

import com.tengu.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * @author 404NotFoundx
 * @date 2019/11/11 23:40
 * @version 1.0.0
 * @since 1.8
 */
public class JdbcTemplate {

    private ConnectionPool pool = ConnectionPool.getPool();

    /**
     * 查询单个结果
     * @param sql sql语句
     * @param obj 需要返回的对象
     * @param args 参数列表
     * @param <T>
     * @return 封装好的对象
     */
    public <T> T queryForObject(String sql, T obj, Object... args) {
        try {
            Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                statement.setString(i, String.valueOf(args[i]));
            }
            ResultSet rset = statement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询多个结果
     * @param sql sql语句
     * @param obj 需要返回的对象
     * @param args 参数列表
     * @param <T>
     * @return 封装好的结果集
     */
    public <T> List<T> queryForList(String sql, T obj, Object... args) {
        return null;
    }

    /**
     * 更新所有实体类中的所有数据，但不包括为空的数据。
     * @param obj 实体类
     * @return 更新条数
     */
    public Long update(Object obj) {
        return null;
    }

    /**
     * 通过SQL语句来更新数据。
     * @param sql sql语句
     * @param args 参数列表
     * @return 更新条数
     */
    public Long update(String sql, Object... args) {
        return null;
    }

    /**
     * 传入一个实体类，将实体类中为空的数据也进行更新。
     * @param obj 实体类
     * @return 更新条数
     */
    public Long updateDoNULL(Object obj) {
        return null;
    }

    /**
     * 通过sql语句插入一条数据
     * @param sql sql语句
     * @param args 参数列表
     * @return 更新条数
     */
    public Long insert(String sql, Object... args) {
        return null;
    }

    /**
     * 通过实体类来更新数据
     * @param model 实体类
     * @return 更新条数
     */
    public <T> Long insert(T model) {
        return null;
    }

    /**
     * 通过sql语句进行删除
     * @param sql sql语句
     * @param args 参数列表
     * @return 更新条数
     */
    public Long delete(String sql, Object... args) {
        return null;
    }

    /**
     * 根据主键进行删除
     * @param id 主键
     * @return 更新条数
     */
    public Long delete(String id) {
        return null;
    }

}
