package com.poseidon.framework.sql;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 关键字，这些关键字都是后面紧跟表名的关键字。
 * 用于解析sql语句中的表名。
 * Create by 2BKeyboard on 2019/12/5 0:06
 */
public class KeyWord {

    @Getter
    List<String> mysql = new ArrayList<>();
    {
        // mysql
        mysql.add("from");
        mysql.add("join");
    }

}
