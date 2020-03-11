package org.poseidon.column;

import org.junit.Test;
import org.raniaia.poseidon.framework.beans.ContextApplication;
import org.raniaia.poseidon.framework.db.JdbcSupport;
import org.raniaia.poseidon.framework.model.database.ColumnModel;

import java.util.List;

/**
 * Copyright by TianSheng on 2020/2/15 1:47
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 */
public class Test0 {

    JdbcSupport jdbc = ContextApplication.getBean("jdbc");

    @Test
    public void getColumn() {
        List<ColumnModel> columnModels = jdbc.queryForList("show full columns from kkb_example_model", ColumnModel.class);
        System.out.println(columnModels.toString());
    }

}
