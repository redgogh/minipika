package org.poseidon;

import org.laniakeamly.poseidon.framework.monitor.LocalDatabase;

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
public class LcoalDatabaseTest {

    public static void main(String[] args) {
        LocalDatabase database = new LocalDatabase();
        System.out.println(((double) 1 / 16));
        database.createTable("test_entry1", "id", "name", "age");
        database.createTable("test_entry2", "id", "name", "age");
        database.createTable("test_entry3", "id", "name", "age");
        database.createTable("test_entry4", "id", "name", "age");
        database.createTable("test_entry5", "id", "name", "age");
        database.createTable("test_entry6", "id", "name", "age");
        database.createTable("test_entry7", "id", "name", "age");
        database.createTable("test_entry8", "id", "name", "age");
        database.createTable("test_entry9", "id", "name", "age");
        database.createTable("test_entry10", "id", "name", "age");
        database.createTable("test_entry11", "id", "name", "age");
        database.createTable("test_entry12", "id", "name", "age");
        database.createTable("test_entry13", "id", "name", "age");
        database.createTable("test_entry14", "id", "name", "age");
        database.createTable("test_entry15", "id", "name", "age");
        database.createTable("test_entry16", "id", "name", "age");
        database.createTable("test_entry17", "id", "name", "age");
        database.createTable("test_entry18", "id", "name", "age");
        database.createTable("test_entry19", "id", "name", "age");
        database.createTable("test_entry21", "id", "name", "age");
        database.createTable("test_entry22", "id", "name", "age");
        database.createTable("test_entry23", "id", "name", "age");
        database.createTable("test_entry24", "id", "name", "age");
        database.createTable("test_entry25", "id", "name", "age");
        database.createTable("test_entry26", "id", "name", "age");
        database.createTable("test_entry27", "id", "name", "age");
        database.createTable("test_entry28", "id", "name", "age");
        database.createTable("test_entry29", "id", "name", "age");
        database.createTable("test_entry30", "id", "name", "age");
        database.createTable("test_entry31", "id", "name", "age");
        database.createTable("test_entry32", "id", "name", "age");
        database.createTable("test_entry33", "id", "name", "age");
        database.createTable("test_entry34", "id", "name", "age");
        database.createTable("test_entry35", "id", "name", "age");
        database.createTable("test_entry36", "id", "name", "age");
        database.createTable("test_entry37", "id", "name", "age");
        database.createTable("test_entry38", "id", "name", "age");
        database.createTable("test_entry20x", "id", "name", "age");
        database.createTable("test_entry201", "id", "name", "age");
        database.createTable("test_entry20d", "id", "name", "age");
        database.createTable("test_entry20a", "id", "name", "age");
        database.createTable("test_entry20s", "id", "name", "age");
        database.createTable("test_entry20ad", "id", "name", "age");
        database.createTable("test_entry204", "id", "name", "age");
        database.createTable("test_entry20xx", "id", "name", "age");
        database.createTable("test_entry20aa", "id", "name", "age");
        database.createTable("test_entry20opo", "id", "name", "age");
        database.createTable("test_entr0", "id", "name", "age");
        database.createTable("test_enty20", "id", "name", "age");
        database.createTable("test_entdry20", "id", "name", "age");
        database.createTable("test_entdary20", "id", "name", "age");
        database.createTable("test_entdaadry20", "id", "name", "age");
        database.createTable("test_entrydad20", "id", "name", "age");
        database.createTable("test_entry2add0", "id", "name", "age");
        database.createTable("test_entry2dw2a0", "id", "name", "age");
        database.createTable("test_entry2ww1ad0", "id", "name", "age");
        database.createTable("test_entry2dwdad0", "id", "name", "age");
        database.createTable("test_entr112y20", "id", "name", "age");
        database.createTable("test_entry22220", "id", "name", "age");
        database.createTable("test_entry233333330", "id", "name", "age");
        database.createTable("test_entry2233230", "id", "name", "age");
        database.createTable("test_entry255550", "id", "name", "age");
        database.createTable("test_entry26666660", "id", "name", "age");
        for (Map.Entry<String, Integer> entry : database.map.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
        database.getVal("test_entry26666660");
        System.out.println();
    }

}
