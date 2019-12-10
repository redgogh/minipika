package com.poseidon.framework.sql;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Create by 2BKeyboard on 2019/12/10 17:34
 */
public class SqlBuilder implements Serializable {

    private boolean and = false;

    private final ArrayList params;

    private final StringBuilder value;

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
        value.append(column).append(" as ").append(alias.concat(", "));
        return this;
    }

    public SqlBuilder selectEnd() {
        int len = value.length();
        value.delete(len - 2, len - 1);
        return this;
    }

    public SqlBuilder from(){
        value.append("from ");
        return this;
    }

    public SqlBuilder addScript(String str){
        value.append(str).append(" ");
        return this;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public Object[] toArray() {
        return params.toArray();
    }

}
