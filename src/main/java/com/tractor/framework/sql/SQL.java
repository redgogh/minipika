package com.tractor.framework.sql;

/**
 * 为了更够更好的拼接sql
 * Create by 2BKeyboard on 2019/12/2 17:21
 */
public class SQL {

    boolean where = false;
    private StringBuilder value = new StringBuilder();

    public SQL() {
    }

    public SQL(String sql) {
        value.append(sql);
    }

    public SQL from(String table) {
        value.append(" from ").append(table);
        return this;
    }

    public SQL where(String k1, String op, String k2) {
        if (!where) {
            value.append(" where ");
            where = true;
        }
        value.append(k1).append(op).append(k2);
        return this;
    }

}
