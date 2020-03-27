package org.raniaia.approve.framework.sql;

/*
 * Copyright (C) 2020 Tiansheng All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * Creates on 2019/12/26.
 */

import lombok.Getter;
import org.raniaia.approve.components.jdbc.JdbcSupport;

import java.util.List;

/**
 * sql执行器
 * @author tiansheng
 */
@SuppressWarnings({"unchecked"})
public class SqlExecute {

    private String sql;
    private Object[] args;
    private Class<?> result;
    private JdbcSupport jdbc;

    @Getter
    private QueryTag label;

    public SqlExecute(){

    }

    /**
     * @param sql       sql script.
     * @param args      script parameters.
     * @param result    result type.
     * @param jdbc      jdbc operation class.
     * @param label     query label.
     *
     * @see SqlMapper#build
     */
    public SqlExecute(String sql, Object[] args, Class<?> result, JdbcSupport jdbc, QueryTag label) {
        this.sql = sql;
        this.jdbc = jdbc;
        this.args = args;
        this.label = label;
        this.result = result;
    }

    public <T> List<T> queryForList(){
        return (List<T>) jdbc.queryForList(sql,result,args);
    }

    public <T> T queryForObject(){
        return (T) jdbc.queryForObject(sql,result,args);
    }

    public int update(){
        return jdbc.update(sql,args);
    }

    public int insert(){
        return jdbc.insert(sql,args);
    }

    public int[] executeBatch(){
        return jdbc.executeBatch(sql,args);
    }

    public boolean execute() {
        return jdbc.execute(sql,args);
    }
}
