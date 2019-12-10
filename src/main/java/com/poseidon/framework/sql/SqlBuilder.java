package com.poseidon.framework.sql;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Create by 2BKeyboard on 2019/12/10 17:34
 */
public class SqlBuilder implements Serializable {

    private ArrayList params;

    private StringBuilder value;

    public SqlBuilder(String str) {
        this.value = new StringBuilder(str).append(" ");
    }

    public void addParam(String column, Object param) {
        params.add(param);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public Object[] toArray(){
        return params.toArray();
    }

}
