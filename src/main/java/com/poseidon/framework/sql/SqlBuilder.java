package com.poseidon.framework.sql;

import com.poseidon.framework.annotation.NotNull;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Create by 2BKeyboard on 2019/12/10 17:34
 */
public class SqlBuilder implements Serializable {

    private boolean and = false;
    private final ArrayList params;
    private final StringBuilder value;

    private int selectLastComma = 0;

    public SqlBuilder() {
        this("");
    }

    public SqlBuilder(String str) {
        this.params = new ArrayList();
        this.value = new StringBuilder(str);
    }

    public SqlBuilder select() {
        value.append("select ");
        return this;
    }

    public SqlBuilder addColumn(String column) {
        value.append(column.concat(", "));
        return this;
    }

    public SqlBuilder addColumn(String column, String alias) {
        value.append(column).append(" as ").append(alias).append(",");
        selectLastComma = value.length();
        value.append(" ");
        return this;
    }

    public SqlBuilder from() {
        value.append("from ");
        return this;
    }

    public SqlBuilder where() {
        value.append("where ");
        return this;
    }

    public SqlBuilder addScript(String str) {
        value.append(str).append(" ");
        return this;
    }

    public SqlBuilder addCondition(String column, String exps, Object param) {
        if(param != null) {
            if (!and) {
                value.append(column).append(" ").append(exps).append(" ").append("? ");
                and = true;
            } else {
                value.append("and ").append(column).append(" ").append(exps).append(" ").append("? ");
            }
            params.add(param);
        }
        return this;
    }

    public SqlBuilder addCondition(String column,String exps,Object param,boolean and) {
        this.and = and;
        return addCondition(column, exps, param);
    }

    public SqlBuilder addLimit(int start,int number){
        value.append("limit ?,?");
        params.add(start);
        params.add(number);
        return this;
    }

    @Override
    public String toString() {
        value.delete(selectLastComma-1,selectLastComma);
        return value.toString();
    }

    public Object[] toArray() {
        return params.toArray();
    }

}
