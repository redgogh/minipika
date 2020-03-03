package org.laniakeamly.poseidon.framework.monitor;

import java.util.Map;

/**
 * <p/>
 * License: <a href="https://github.com/Laniakeamly/poseidon/blob/master/LICENSE">Apache License 2.0</a>
 * <p/>
 * Copyright: Create by TianSheng on 2019/12/17 18:29
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 */
public interface Database {

    /**
     * 创建表
     * create a new data table.
     *
     * @param tableName 表名 | table name
     * @param columns   字段 | table column
     */
    void createTable(String tableName, String... columns);

    /**
     * 查询一张表的所有数据
     * query all data for table.
     *
     * @param table 表名 | table name
     * @return json
     */
    String queryForList(String table);

    /**
     * 添加一条新的数据
     * insert a new data.
     * @param table 表名 | table name
     * @param value 需要插入的数据 | the data
     */
    void insert(String table, Map<String,Object> value);

}
