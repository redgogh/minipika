package org.poseidon;

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



import net.sf.jsqlparser.JSQLParserException;
import org.raniaia.approve.framework.tools.SQLUtils;

/**
 * Copyright: Create by tiansheng on 2019/12/6 16:02
 */
public class SQLParserExample {

    static String sql = "select * from user_model as u,user_money as m left join product_model as p on u.name = p.name where id = 1";

    public static void main(String[] args) throws JSQLParserException {
        System.out.println(SQLUtils.getSQLTables(sql));
    }

}
