package org.laniakeamly.poseidon.framework.db;

import org.laniakeamly.poseidon.framework.sql.SqlMapper;

import java.util.List;

/**
 * Create by 2BKeyboard on 2019/12/19 14:12
 */
public interface JdbcBuilder {

    /**
     * 查询并返回对象
     * @param sqlMapper sql对象
     * @param <T>
     * @return
     */
    <T> T queryForObject(SqlMapper sqlMapper);

    /**
     * 查询多个结果
     * @param sqlMapper sql对象
     * @param <T>
     * @return 封装好的结果集
     */
    <T> List<T> queryForList(SqlMapper sqlMapper);

    /**
     * 查询并返回JSON字符串
     * @param sqlMapper sql对象
     * @return
     */
    String queryForJson(SqlMapper sqlMapper);

    /**
     * 分页查询,SQL不用加limit
     * @param sqlMapper     sql对象
     * @param pageVo        分页插件
     * @return              分页插件
     */
    NativePageHelper queryForPage(SqlMapper sqlMapper, NativePageHelper pageVo);

    /**
     * 通过SQL语句来更新数据。
     * @param sqlMapper sql语句
     * @return 更新条数
     */
    int update(SqlMapper sqlMapper);

    /**
     * 通过sql语句插入一条数据
     * @param sqlMapper SQL对象
     * @return 更新条数
     */
    int insert(SqlMapper sqlMapper);

}
